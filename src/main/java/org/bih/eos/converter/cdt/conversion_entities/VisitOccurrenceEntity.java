package org.bih.eos.converter.cdt.conversion_entities;

import org.bih.eos.exceptions.UnprocessableEntityException;
import org.bih.eos.jpabase.model.entity.Concept;
import org.bih.eos.jpabase.model.entity.Person;
import org.bih.eos.jpabase.model.entity.VisitOccurrence;

import java.util.Date;
import java.util.Optional;

public class VisitOccurrenceEntity extends EntityWithStandardConcept<VisitOccurrence> {


    public VisitOccurrenceEntity(VisitOccurrence jpaEntity, Person person, Concept type) {
        super(jpaEntity, person, type);
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

    public void setEndDate(Optional<Date> date) {
        populateFieldIfPresent(date, dateTimeValue -> {
            jpaEntity.setVisitEndDate(dateTimeValue);
            jpaEntity.setVisitEndDateTime(dateTimeValue);
        });
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
}
