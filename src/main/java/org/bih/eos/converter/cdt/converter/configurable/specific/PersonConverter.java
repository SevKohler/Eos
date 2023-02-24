package org.bih.eos.converter.cdt.converter.configurable.specific;

import com.nedap.archie.rm.archetyped.Locatable;
import com.nedap.archie.rm.datastructures.Element;
import com.nedap.archie.rm.datavalues.DvText;
import com.nedap.archie.rm.datavalues.quantity.datetime.DvDate;
import com.nedap.archie.rm.datavalues.quantity.datetime.DvDateTime;
import com.nedap.archie.rm.datavalues.quantity.datetime.DvDuration;
import org.bih.eos.converter.PathProcessor;
import org.bih.eos.converter.cdt.DefaultConverterServices;
import org.bih.eos.converter.cdt.conversion_entities.PersonEntity;
import org.bih.eos.converter.cdt.converter.configurable.generic.CDTPersonConverter;
import org.bih.eos.jpabase.model.entity.Concept;
import org.bih.eos.jpabase.model.entity.JPABaseEntity;
import org.bih.eos.jpabase.model.entity.Person;
import org.bih.eos.yaml.ValueEntry;
import org.bih.eos.yaml.cdt_configs.person.PersonConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Optional;

public class PersonConverter extends CDTPersonConverter {

    private static final Logger LOG = LoggerFactory.getLogger(PersonConverter.class);

    public PersonConverter(DefaultConverterServices defaultConverterServices, PersonConfig personConfig) {
        super(defaultConverterServices, personConfig);
    }

    @Override
    protected Optional<JPABaseEntity> convertInternal(Locatable locatable) {
        PersonConfig personConfig = (PersonConfig) omopMapping;
        PersonEntity person = new PersonEntity(new Person(), personConfig);
        person = convertRequiredFields(locatable, personConfig, person);
        person = convertOptionalFields(locatable, personConfig, person);
        return person.toJpaEntity();
    }

    @Override
    protected PersonEntity convertRequiredFields(Locatable locatable, PersonConfig config, PersonEntity person) {
        convertGenderConcept(locatable, config.getGenderConcept().getAlternatives(), person);
        convertRaceConcept(locatable, config, person);
        convertYearsOfBirth(locatable, config.getYearOfBirth().getAlternatives(), person);
        convertEthnicityConcept(locatable, config.getEthnicityConcept().getAlternatives(), person);
        return person;
    }

    @Override
    protected PersonEntity convertOptionalFields(Locatable contentItem, PersonConfig config, PersonEntity person) {
        if(config.getBirthDatetime().isPopulated()){
            convertBirthDatetime(contentItem, config.getBirthDatetime().getAlternatives() ,person);
        }
        if(config.getLocationId().isPopulated()){
            unsupportedMapping("location_id");
        }
        if(config.getCareSiteId().isPopulated()){
            unsupportedMapping("care_site_id");
        }
        if(config.getProviderId().isPopulated()){
            unsupportedMapping("provider_id");
        }
        return person;
    }

    private void convertBirthDatetime(Locatable contentItem, ValueEntry[] alternatives, PersonEntity person) {
        Optional<Date> date = (Optional<Date>) dateTimeConverter.convert(contentItem, alternatives);
        if(date.isPresent()){
            Calendar myCal = new GregorianCalendar();
            myCal.setTime(date.get());
            person.setBirthDateTime(date, Optional.of(myCal.get(Calendar.MONTH)), Optional.of(myCal.get(Calendar.DAY_OF_MONTH)));
        }else{
            person.setBirthDateTime(Optional.empty(), Optional.empty(), Optional.empty());
        }
    }

    private void convertGenderConcept(Locatable contentItem, ValueEntry[] genderConcept, PersonEntity person) {
        person.setGenderConcept((Optional<Concept>) standardConceptConverter.convert(contentItem, genderConcept));
        person.setGenderSourceConcept((Optional<Concept>) sourceConceptConverter.convert(contentItem, genderConcept));
        person.setGenderSourceValue((Optional<String>) sourceValueConverter.convert(contentItem, genderConcept));
    }

    private void convertRaceConcept(Locatable contentItem, PersonConfig config, PersonEntity person) {
        if (config.getRaceConcept() != null) {
            ValueEntry[] alternatives = config.getRaceConcept().getAlternatives();
            person.setRaceConcept((Optional<Concept>) standardConceptConverter.convert(contentItem, alternatives));
            person.setRaceSourceConcept((Optional<Concept>) sourceConceptConverter.convert(contentItem, alternatives));
            person.setRaceSourceValue((Optional<String>) sourceValueConverter.convert(contentItem, alternatives));
        } else {
            Optional<Concept> concept = Optional.of(defaultConverterServices.getConceptService().findById(0L));
            person.setRaceConcept(concept);
            person.setRaceSourceConcept(concept);
            person.setRaceSourceValue(Optional.of("0"));
        }
    }

    private PersonEntity convertYearsOfBirth(Locatable contentItem, ValueEntry[] alternatives, PersonEntity person) {
        for (ValueEntry valueEntry : alternatives) {
            if (valueEntry.getCode() != null) {
                person.setYearOfBirth(Optional.of(valueEntry.getCode().intValue()));
            } else if (valueEntry.getPath() != null && PathProcessor.getItemAtPath(contentItem, valueEntry.getPath()).isPresent()) {
                return convertYearPath(contentItem, valueEntry.getPath(), person);
            }
        }
        return person;
    }

    private void convertEthnicityConcept(Locatable contentItem, ValueEntry[] alternatives, PersonEntity person) {
        person.setEthnicityConcept((Optional<Concept>) standardConceptConverter.convert(contentItem, alternatives));
        person.setEthnicitySourceConcept((Optional<Concept>) sourceConceptConverter.convert(contentItem, alternatives));
        person.setEthnicitySourceValue((Optional<String>) sourceValueConverter.convert(contentItem, alternatives));
    }

    private PersonEntity convertYearPath(Locatable contentItem, String path, PersonEntity person) {
        Optional<?> valueItem = PathProcessor.getItemAtPath(contentItem, path);
        if (valueItem.isPresent()) {
            if (valueItem.get().getClass() == Element.class) {
                convertElement((Element) valueItem.get(), person);
            } else {
                LOG.warn("Mapping for Person is ignored, since the year of birth is not an Element");
            }
        }
        return person;
    }


    private void convertElement(Element element, PersonEntity person) {
        if (element.getValue().getClass() == DvDuration.class) {
            person.setYearOfBirth(convertDvDuration((DvDuration) element.getValue()));
        } else if (element.getValue().getClass() == DvText.class) {
            convertDvText((DvText) element.getValue(), person);
        } else if (element.getValue().getClass() == DvDate.class) {
            convertDvDate((DvDate) element.getValue(), person);
        } else if (element.getValue().getClass() == DvDateTime.class) {
            convertDvDateTime((DvDateTime) element.getValue(), person);
        } else {
            LOG.warn("Mapping for Person is ignored, since the year of birth is not an Dv Duration Dv Text or Dv Date.");
        }
    }

    private void convertDvDate(DvDate dvDate, PersonEntity person) {
        if (dvDate.getValue() != null) {
            if(dvDate.getValue().getClass() == LocalDate.class){
                LocalDate localDate = (LocalDate) dvDate.getValue();
                person.setYearOfBirth(Optional.of(localDate.getYear()));
            }else if(dvDate.getValue().getClass() == OffsetDateTime.class){
                OffsetDateTime offsetDateTime = (OffsetDateTime) dvDate.getValue();
                person.setYearOfBirth(Optional.of(offsetDateTime.getYear()));
            }else{
                LOG.warn("DvDate cannot be parsed to OffsetDateTime or LocalDate. Year of birth is set empty. a");
                person.setYearOfBirth(Optional.empty());
            }
        } else {
            LOG.warn("DvDate value is empy, archetype mapping is ignored");
            person.setYearOfBirth(Optional.empty());
        }
    }

    private Optional<Integer> convertDvDuration(DvDuration dvDuration) {
        if (dvDuration.getValue() != null) {
            if (dvDuration.getValue().toString().contains("Y")) {
                try {
                    OffsetDateTime now = OffsetDateTime.now();
                    int year = now.getYear() - dvDuration.getMagnitude().intValue();
                    return Optional.of(year);
                } catch (NumberFormatException numberFormatException) {
                    LOG.warn("Duration cotains no integer value, archetype mapping is skipped.");
                    return Optional.empty();
                }
            }
        }
        return Optional.empty();
    }

    private void convertDvText(DvText value, PersonEntity person) {
        try {
            person.setYearOfBirth(Optional.of(Integer.valueOf(value.getValue())));
        } catch (NumberFormatException numberFormatException) {
            LOG.warn("DVText contains no number, conversion of this archetype is skipped.");
            person.setYearOfBirth(Optional.empty());
        }
    }

    private void convertDvDateTime(DvDateTime dvDateTime, PersonEntity person) {
        if (dvDateTime.getValue() != null) {
            OffsetDateTime dateTime = OffsetDateTime.parse(dvDateTime.getValue().toString());
            person.setYearOfBirth(Optional.of(dateTime.getYear()));
        } else {
            LOG.warn("DVDateTime value is empy, archetype mapping is ignored");
            person.setYearOfBirth(Optional.empty());
        }
    }

}//49225db0-e39b-4de7-b9b9-c2f7b620d76a