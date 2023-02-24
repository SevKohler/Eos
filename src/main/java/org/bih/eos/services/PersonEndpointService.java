package org.bih.eos.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.nedap.archie.rm.composition.Composition;
import com.nedap.archie.rm.ehr.EhrStatus;
import org.bih.eos.config.PersonConverterProperties;
import org.bih.eos.controller.dao.Ehrs;
import org.bih.eos.exceptions.UnprocessableEntityException;
import org.bih.eos.fileloader.FileLoader;
import org.bih.eos.jpabase.dba.service.ConceptService;
import org.bih.eos.jpabase.dba.service.EHRToPersonService;
import org.bih.eos.jpabase.dba.service.PersonService;
import org.bih.eos.jpabase.model.entity.EHRToPerson;
import org.bih.eos.jpabase.model.entity.JPABaseEntity;
import org.bih.eos.jpabase.model.entity.Person;
import org.bih.eos.yaml.Mapping;
import org.ehrbase.client.aql.query.NativeQuery;
import org.ehrbase.client.aql.query.Query;
import org.ehrbase.client.aql.record.Record1;
import org.ehrbase.client.openehrclient.OpenEhrClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
public class PersonEndpointService {

    private static final Logger LOG = LoggerFactory.getLogger(PersonEndpointService.class);

    private final ConceptService conceptService;
    private final PersonService personService;
    private final EHRToPersonService ehrToPersonService;
    private final OpenEhrClient openEhrClient;
    private final ConverterService converterService;
    private final PersonConverterProperties personConverterProperties;
    private final List<String> personConversionArchetypeIds = new ArrayList();

    public PersonEndpointService(ConceptService conceptService, PersonService personService, EHRToPersonService ehrToPersonService, OpenEhrClient openEhrClient, ConverterService converterService, PersonConverterProperties personConverterProperties) throws IOException {
        this.conceptService = conceptService;
        this.personService = personService;
        this.ehrToPersonService = ehrToPersonService;
        this.openEhrClient = openEhrClient;
        this.converterService = converterService;
        this.personConverterProperties = personConverterProperties;
        if (personConverterProperties.getMode() == PersonConverterProperties.Mode.CONVERSION) {
            loadPersonMappingArchetypeIds();
        }
    }

    private void loadPersonMappingArchetypeIds() throws IOException {
        ObjectMapper om = new ObjectMapper(new YAMLFactory()).setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE);
        List<File> configsFiles = FileLoader.loadFiles("mapping_config/person_data", "Error loading converter configs from mappings_config/person, where any configs provided?");
        for (File file : Objects.requireNonNull(configsFiles)) {
            if (file.isFile()) {
                LOG.info("Loaded mapping conig: " + file);
                Mapping mapping = om.readValue(file, Mapping.class);
                personConversionArchetypeIds.add(mapping.getArchetypeId());
            }
        }
    }

    public List<EHRToPerson> createEhr(List<UUID> ehrs) {
        List<EHRToPerson> output = new ArrayList<>();
        for (UUID ehr : ehrs) {
            output.add(createEHRToPerson(ehr));
        }
        return output;
    }

    public Object createEhr(Ehrs ehrs) {
        List<EHRToPerson> output = new ArrayList<>();
        for (String ehrId : ehrs.getEhrIds()) {
            Optional<EhrStatus> ehrStatus = openEhrClient.ehrEndpoint().getEhrStatus(UUID.fromString(ehrId));
            if (ehrStatus.isPresent()) {
                output.add(createEHRToPerson(UUID.fromString(ehrId)));
            } else {
                return "The ehr id " + ehrId + " does not exist in the platform. Nothing was created.";
            }
        }
        return output;
    }

    private EHRToPerson createEHRToPerson(UUID ehr) {
        if (ehrToPersonService.findByEhrId(ehr.toString()).isPresent()) {
            return ehrToPersonService.findByEhrId(ehr.toString()).get(); // already existent
        } else {
            if (personConverterProperties.getMode() == PersonConverterProperties.Mode.AUTOMATIC) {
                return generateEmptyPerson(ehr);
            } else {
                return runPersonConverter(ehr);
            }
        }
    }

    private EHRToPerson runPersonConverter(UUID ehrId) {
        List<JPABaseEntity> conversionResults = new ArrayList<>();
        for (String archetypeId : personConversionArchetypeIds) {
            List<Record1<Composition>> result = executeAqlQuery(buildCompositionQuery(ehrId, archetypeId));
            if (result.size() == 1) {
                conversionResults.addAll(converterService.convert(result.get(0).value1()));
            } else {
                throw new UnprocessableEntityException("The Ehr has several compositions for the person to be mapped. Only one has to be provided");
            }
        }
        if(conversionResults.size()>1){
            throw new UnprocessableEntityException("The Ehr has several compositions for the person to be mapped. Only one has to be provided");
        }else{
            return createEhrToPerson((Person) conversionResults.get(0), ehrId);
        }

    }

    private EHRToPerson createEhrToPerson(Person person, UUID ehrId) {
        EHRToPerson ehrToPerson = new EHRToPerson();
        ehrToPerson.setEhrId(ehrId.toString());
        ehrToPerson.setPerson(person);
        ehrToPerson = ehrToPersonService.save(ehrToPerson);
        return ehrToPerson;
    }

    private NativeQuery<Record1<Composition>> buildCompositionQuery(UUID ehrId, String archetypeId) {
        return Query.buildNativeQuery("Select c from EHR e CONTAINS Composition c WHERE EXISTS c/content[" + archetypeId + "] AND e/ehr_id/value='" + ehrId + "'", Composition.class);
    }

    private List<Record1<Composition>> executeAqlQuery(Query<Record1<Composition>> query) {
        try {
            return openEhrClient.aqlEndpoint().execute(query);
        } catch (NullPointerException nullPointerException) {
            return new ArrayList<>();
        }
    }

    private EHRToPerson generateEmptyPerson(UUID ehr) {
        Person person = new Person();
        person.setGenderSourceConcept(conceptService.findById(0L));
        person.setRaceSourceConcept(conceptService.findById(0L));
        person.setEthnicitySourceConcept(conceptService.findById(44789059L));
        person = personService.create(person);
        EHRToPerson ehrToPerson = new EHRToPerson();
        ehrToPerson.setEhrId(ehr.toString());
        ehrToPerson.setPerson(person);
        ehrToPerson = ehrToPersonService.save(ehrToPerson);
        return ehrToPerson;
    }


}