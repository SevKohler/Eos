package org.bih.eos.converter.cdt.conversion_entities;

import org.bih.eos.converter.cdt.converter.nonconfigurable.VisitOccurrenceConverter;
import org.bih.eos.exceptions.UnprocessableEntityException;
import org.bih.eos.jpabase.entity.Concept;
import org.bih.eos.jpabase.entity.Person;
import org.bih.eos.jpabase.entity.VisitOccurrence;
import org.bih.eos.yaml.cdt_configs.visit.VisitConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.Optional;

public class VisitOccurrenceEntity extends EntityWithStandardConcept<VisitOccurrence> {
    private static final Logger LOG = LoggerFactory.getLogger(VisitOccurrenceEntity.class);
    private final VisitConfig config;

    public VisitOccurrenceEntity(VisitConfig config, Person person) {
        super(new VisitOccurrence(), person, null); // visit_type could be defined in configuration
        this.config = config;
    }
    
    public VisitOccurrenceEntity(VisitConfig config, Person person, Concept type) {
        super(new VisitOccurrence(), person, type);
        this.config = config;
    }
    
    public VisitOccurrenceEntity(VisitOccurrence jpaEntity, Person person, Concept type) {
        super(jpaEntity, person, type);
        config=null;
    }

    @Override
    protected Boolean validateRequiredOptionalsNotNull() {
        return true; //TODO implement
    }

    @Override
    protected void setPersonAndType(Person person, Concept type) {
        jpaEntity.setPerson(person);
        jpaEntity.setVisitTypeConcept(type);
    }

    @Override
    public void setStandardConcept(Optional<Concept> conceptCode) {
        if(conceptCode.isEmpty()){
            throw new UnprocessableEntityException("InPatient Visit could not be found in the vocabulary of your omop Database, it is mandatory that this concept exists.");
        }
        populateFieldIfPresent(conceptCode, conceptValue -> jpaEntity.setVisitConcept(conceptValue));
    }

    @Override
    public void setEntitySourceValue(Optional<String> entitySourceValue) {
        //ignored field doesnt exist
    }

    public void setEndDate(Optional<Date> endDate, Optional<Date> startDate) {
        if(endDate.isPresent()){
            populateFieldIfPresent(endDate, dateTimeValue -> {
                jpaEntity.setVisitEndDate(dateTimeValue);
                jpaEntity.setVisitEndDateTime(dateTimeValue);
            });
        }else if(startDate.isPresent()){ //Set StartDate as EndDate since Enddate is emtpy
            LOG.warn("EndDate time was empty provided by the composition, startDate is set instead for Visit_occurrence");
            populateFieldIfPresent(startDate, dateTimeValue -> {
                jpaEntity.setVisitEndDate(dateTimeValue);
                jpaEntity.setVisitEndDateTime(dateTimeValue);
            });
        }
    }

    public void setDateStartAndEnd(Optional<Date> startDate, Optional<Date> endDate) {
        setDateTime(startDate);
        setEndDate(endDate, startDate);
    }

    @Override
    public void setDateTime(Optional<Date> date) {
        populateFieldIfPresent(date, dateTimeValue -> {
            jpaEntity.setVisitStartDate(dateTimeValue);
            jpaEntity.setVisitStartDateTime(dateTimeValue);
        });
    }

    public void setAdmittedFromConcept(Concept concept){
        jpaEntity.setAdmittedFromConcept(concept);
    }

    public void setDischargeToConcept(Concept concept){
        jpaEntity.setDischargedToConcept(concept);
    }
    
    public void setTypeConcept(Optional<Concept> concept){
        if(concept.isEmpty()){
            throw new UnprocessableEntityException("Type Visit could not be found in the vocabulary of your omop Database, it is mandatory that this concept exists.");
        }
        populateFieldIfPresent(concept, conceptValue -> jpaEntity.setVisitTypeConcept(conceptValue));
    }
}
