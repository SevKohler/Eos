package org.bih.eos.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import org.bih.eos.converter.composition.CompositionToMDConverter;
import org.bih.eos.converter.cdt.converter.CDTConverter;
import org.bih.eos.converter.cdt.DefaultConverterServices;
import org.bih.eos.converter.composition.CompositionToPersonConverter;
import org.bih.eos.converter.cdm_field.concept.DVTextCodeToConceptConverter;
import org.bih.eos.exceptions.UnprocessableEntityException;
import org.bih.eos.fileloader.FileLoader;
import org.bih.eos.jpabase.dba.service.ConceptRelationshipService;
import org.bih.eos.jpabase.dba.service.ConceptService;
import org.bih.eos.services.ConceptSearchService;
import org.bih.eos.services.PersistenceService;
import org.bih.eos.yaml.Mapping;
import org.bih.eos.yaml.OmopMapping;
import org.bih.eos.yaml.cdt_configs.CDTMappingConfig;
import org.bih.eos.yaml.cdt_configs.person.PersonConfig;
import org.bih.eos.yaml.non_cdt.IncludeConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.ConverterRegistry;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

@Configuration
public class ConversionAutoConfiguration implements InitializingBean {

    private static final Logger LOG = LoggerFactory.getLogger(ConversionAutoConfiguration.class);
    private final String mdDataConfigs = "mapping_config/medical_data";
    private final String personDataConfigs = "mapping_config/person_data";
    private final ConverterRegistry converterRegistry;
    private final ConceptService conceptService;
    private final ConceptRelationshipService conceptRelationshipService;
    private final PersistenceService persistenceService;
    private final PersonConverterProperties personConverterProperties;


    public ConversionAutoConfiguration(ConverterRegistry converterRegistry,
                                       ConceptService conceptService,
                                       ConceptRelationshipService conceptRelationshipService,
                                       PersistenceService persistenceService,
                                       PersonConverterProperties personConverterProperties) {
        this.converterRegistry = converterRegistry;
        this.conceptService = conceptService;
        this.conceptRelationshipService = conceptRelationshipService;
        this.persistenceService = persistenceService;
        this.personConverterProperties = personConverterProperties;
    }

    @Override
    public void afterPropertiesSet() throws IOException {
        ConceptSearchService conceptSearchService = new ConceptSearchService(conceptService, conceptRelationshipService);
        DefaultConverterServices defaultConverterServices = new DefaultConverterServices(conceptService, conceptSearchService, new DVTextCodeToConceptConverter(conceptSearchService), persistenceService);
        loadPDMappings(defaultConverterServices);
        loadMDMappings(defaultConverterServices);
    }

    private void loadMDMappings(DefaultConverterServices defaultConverterServices) throws IOException {
        List<Mapping> mappingsMD = loadMappingConfigs(mdDataConfigs);
        HashMap<String, List<CDTConverter>> conversionMapMedicalData = new HashMap<>();
        for (Mapping mapping : mappingsMD) {
            putMapData(conversionMapMedicalData, mapping.getArchetypeId(), initialiseConvertersMedicalData(mapping.getMappings(), defaultConverterServices));
        }
        loadIncludes(mappingsMD, defaultConverterServices, conversionMapMedicalData);
        converterRegistry.addConverter(new CompositionToMDConverter(conversionMapMedicalData, defaultConverterServices));
    }

    private void loadIncludes(List<Mapping> loadedMappings, DefaultConverterServices defaultConverterServices, HashMap<String, List<CDTConverter>> conversionMapMedicalData) {
        for (Mapping mapping : loadedMappings) {
            for (OmopMapping omopMapping : mapping.getMappings()) {
                if (omopMapping.getClass() == IncludeConfig.class) {
                    findIncludedMappingAndResolve((IncludeConfig) omopMapping, mapping, loadedMappings, conversionMapMedicalData, defaultConverterServices);
                }
            }
        }
    }

    private void findIncludedMappingAndResolve(IncludeConfig includeMapping, Mapping mappingWithInclude, List<Mapping> mappings, HashMap<String, List<CDTConverter>> conversionMapMedicalData, DefaultConverterServices defaultConverterServices) {
        for (Mapping mapping : mappings) {
            if (mapping.getArchetypeId().equals(includeMapping.getArchetypeId())) {
                solveInclude(includeMapping, mappingWithInclude, mapping, conversionMapMedicalData, defaultConverterServices);
            }
        }
    }

    private void solveInclude(IncludeConfig includeMapping, Mapping mappingWithInclude, Mapping mapping, HashMap<String, List<CDTConverter>> conversionMapMedicalData, DefaultConverterServices defaultConverterServices) {
        for (OmopMapping omopMapping : mapping.getMappings()) {
            if (CDTMappingConfig.class.isAssignableFrom(omopMapping.getClass())) {
                CDTMappingConfig importedMapping = getClone(omopMapping);
                importMapping(includeMapping, importedMapping, mappingWithInclude, conversionMapMedicalData, defaultConverterServices);
            }
        }
    }

    private void importMapping(IncludeConfig includeMapping, CDTMappingConfig importedMapping, Mapping mappingWithInclude, HashMap<String, List<CDTConverter>> conversionMapMedicalData, DefaultConverterServices defaultConverterServices) {
        if (includeMapping.getBasePath() != null) { //TODO refactor
            importedMapping.setBasePath(includeMapping.getBasePath());
            List<CDTConverter> cdtConverters = conversionMapMedicalData.get(mappingWithInclude.getArchetypeId());
            if(cdtConverters == null){
                cdtConverters = new ArrayList<>();
            }
            cdtConverters.add(getConverterMD(importedMapping, defaultConverterServices));
            conversionMapMedicalData.put(mappingWithInclude.getArchetypeId(), cdtConverters);
        }
    }

    private CDTMappingConfig getClone(OmopMapping omopMapping) {
        try {
            return (CDTMappingConfig) omopMapping.clone();
        } catch (CloneNotSupportedException e) {
            throw new UnprocessableEntityException("The omopMapping could not have been cloned there is an error in the class logic !");
        }
    }

    private void loadPDMappings(DefaultConverterServices defaultConverterServices) throws IOException {
        List<Mapping> mappingsPD = loadMappingConfigs(personDataConfigs);
        HashMap<String, List<CDTConverter>> conversionPersonData = new HashMap<>();
        for (Mapping mapping : mappingsPD) {
            putMapData(conversionPersonData, mapping.getArchetypeId(), initialiseConvertersPersonData(mapping.getMappings(), defaultConverterServices));
        }
        converterRegistry.addConverter(new CompositionToPersonConverter(conversionPersonData, defaultConverterServices));
    }

    private void putMapData(HashMap<String, List<CDTConverter>> conversionMap, String archetypeId, List<CDTConverter> converters) {
        if (converters.size() > 0) {
            conversionMap.put(archetypeId, converters);
        }
    }

    private List<Mapping> loadMappingConfigs(String configFolder) throws IOException {
        List<Mapping> mappings = new ArrayList<>();
        ObjectMapper om = new ObjectMapper(new YAMLFactory()).setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE);
        List<String> configuredArchetypeMappings = new ArrayList<>();
        List<File> configsFiles = loadConverterConfigFiles(configFolder);
        for (File file : Objects.requireNonNull(configsFiles)) {
            initialiseConverterConfig(file, om, mappings, configuredArchetypeMappings);
        }
        return mappings;
    }

    private List<File> loadConverterConfigFiles(String configFolder) {
        return FileLoader.loadFiles(configFolder, "Error loading converter configs from mapping_config/, where any configs provided?");
    }

    private void initialiseConverterConfig(File file, ObjectMapper om, List<Mapping> mappings, List<String> configuredArchetypeMappings) throws IOException {
        if (file.isFile()) {
            LOG.info("Loaded mapping conig: " + file);
            Yaml yaml = new Yaml();
            FileInputStream input = new FileInputStream(file);
            Object yamlObj = yaml.load(input); //use Snake YAML in order to support Anchors and then parse it into Jackson (does not support anchors)
            String jsonString = om.writerWithDefaultPrettyPrinter().writeValueAsString(yamlObj);
            Mapping mapping = om.readValue(jsonString, Mapping.class);
            if (!configuredArchetypeMappings.contains(mapping.getArchetypeId())) {
                configuredArchetypeMappings.add(mapping.getArchetypeId());
                mappings.add(mapping);
            } else {
                throw new UnprocessableEntityException("The mapping for the archetype " + mapping.getArchetypeId() + " is duplicated");
            }
        }
    }

    private List<CDTConverter> initialiseConvertersMedicalData(List<OmopMapping> mappingList, DefaultConverterServices defaultConverterServices) {
        List<CDTConverter> list = new ArrayList<>();
        for (OmopMapping mapping : mappingList) {
            if (mapping.getClass() != PersonConfig.class && mapping.getClass() != IncludeConfig.class) {
                list.add(getConverterMD(mapping, defaultConverterServices));
            }
        }
        return list;
    }

    private CDTConverter getConverterMD(OmopMapping mapping, DefaultConverterServices defaultConverterServices) {
        return mapping.getConverterInstance(defaultConverterServices);
    }

    private List<CDTConverter> initialiseConvertersPersonData(List<OmopMapping> mappingList, DefaultConverterServices defaultConverterServices) {
        List<CDTConverter> list = new ArrayList<>();
        for (OmopMapping mapping : mappingList) {
            if (mapping.getClass() == PersonConfig.class) {
                addPersonConverter(list, mapping, defaultConverterServices);
            }
        }
        return list;
    }


    private void addPersonConverter(List<CDTConverter> list, OmopMapping mapping, DefaultConverterServices defaultConverterServices) {
        if (personConverterProperties.getMode() == PersonConverterProperties.Mode.CONVERSION) {
            CDTConverter abstractArchetypeConverter = mapping.getConverterInstance(defaultConverterServices);
            list.add(abstractArchetypeConverter);
        }
    }

}
