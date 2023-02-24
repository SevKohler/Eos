package org.bih.eos.converter.cdt.conversion_entities;

import org.bih.eos.jpabase.model.entity.Concept;
import org.bih.eos.jpabase.model.entity.Person;
import org.bih.eos.yaml.cdt_configs.person.*;

import java.util.Date;
import java.util.Optional;


public class PersonEntity extends Entity<Person> {

    private final PersonBirthDatetime birthDatetimeOptional;
    private final PersonProvider providerOptional;
    private final PersonLocation locationOptional;
    private final PersonCareSite careSiteOptional;

    public PersonEntity(Person jpaEntity, PersonConfig personConfig) {
        super(jpaEntity);
        this.birthDatetimeOptional = personConfig.getBirthDatetime();
        this.providerOptional = personConfig.getProviderId();
        this.locationOptional = personConfig.getLocationId();
        this.careSiteOptional = personConfig.getCareSiteId();
    }


    @Override
    protected Boolean validateRequiredOptionalsNotNull() {
        return birthDatetimeOptional.validate(jpaEntity)
                && providerOptional.validate(jpaEntity)
                && locationOptional.validate(jpaEntity)
                && careSiteOptional.validate(jpaEntity);
    }

    public void setGenderConcept(Optional<Concept> conceptCode) {
        populateFieldIfPresent(conceptCode, entitySourceValueValue -> jpaEntity.setGenderConcept(entitySourceValueValue));
    }

    public void setGenderSourceConcept(Optional<Concept> sourceConcept) {
        populateFieldIfPresent(sourceConcept, entitySourceValueValue -> jpaEntity.setGenderSourceConcept(entitySourceValueValue));
    }

    public void setGenderSourceValue(Optional<String> entitySourceValue) {
        populateFieldIfPresent(entitySourceValue, entitySourceValueValue -> jpaEntity.setGenderSourceValue(entitySourceValueValue));
    }

    public void setYearOfBirth(Optional<Integer> age) {
        populateFieldIfPresent(age, ageValue -> {
            jpaEntity.setYearOfBirth(ageValue);
        });
    }

    public void setRaceConcept(Optional<Concept> concept) {
        populateFieldIfPresent(concept, conceptCode -> jpaEntity.setRaceConcept(conceptCode));
    }

    public void setRaceSourceConcept(Optional<Concept> concept) {
        populateFieldIfPresent(concept, conceptCode -> jpaEntity.setRaceSourceConcept(conceptCode));
    }

    public void setRaceSourceValue(Optional<String> entitySourceValue) {
        populateFieldIfPresent(entitySourceValue, entitySourceValueValue -> jpaEntity.setRaceSourceValue(entitySourceValueValue));
    }

    public void setEthnicityConcept(Optional<Concept> concept) {
        populateFieldIfPresent(concept, conceptCode -> jpaEntity.setEthnicityConcept(conceptCode));
    }

    public void setEthnicitySourceConcept(Optional<Concept> concept) {
        populateFieldIfPresent(concept, conceptCode -> jpaEntity.setEthnicitySourceConcept(conceptCode));
    }

    public void setEthnicitySourceValue(Optional<String> entitySourceValue) {
        populateFieldIfPresent(entitySourceValue, entitySourceValueValue -> jpaEntity.setEthnicitySourceValue(entitySourceValueValue));
    }

    public void setBirthDateTime(Optional<Date> dateTime, Optional<Integer> day, Optional<Integer> month) {
        populateFieldIfPresent(dateTime, birthDatetimeOptional.isOptional(), dateTimeValue -> jpaEntity.setBirthDateTime(dateTimeValue));
        populateFieldIfPresent(day, birthDatetimeOptional.isOptional(), dayValue -> jpaEntity.setDayOfBirth(dayValue));
        populateFieldIfPresent(month, birthDatetimeOptional.isOptional(), monthValue -> jpaEntity.setMonthOfBirth(monthValue));
    }
}
