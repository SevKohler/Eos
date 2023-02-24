package org.bih.eos.converter.cdt.conversion_entities;

import org.bih.eos.jpabase.model.entity.Concept;
import org.bih.eos.jpabase.model.entity.ConditionOccurrence;
import org.bih.eos.jpabase.model.entity.Person;
import org.bih.eos.jpabase.model.entity.VisitOccurrence;
import org.bih.eos.yaml.cdt_configs.condition_occurrence.*;

import java.util.Date;
import java.util.Optional;

public class ConditionOccurrenceEntity extends EntityWithSourceConcept<ConditionOccurrence> {

    private final ConditionStatusConcept statusOptional;
    private final ConditionEndDate endTimeOptional;
    private final ConditionStopReason stopReasonOptional;
    private final ConditionProvider providerOptional;
    private final ConditionVisitDetail visitDetailOptional;

    public ConditionOccurrenceEntity(ConditionOccurrenceConfig cdtMappingConfig, Person person, Concept type) {
        super(new ConditionOccurrence(), person, type);
        this.statusOptional = cdtMappingConfig.getConditionStatusConceptId();
        this.endTimeOptional = cdtMappingConfig.getConditionEndDate();
        this.stopReasonOptional = cdtMappingConfig.getStopReason();
        this.providerOptional = cdtMappingConfig.getProviderId();
        this.visitDetailOptional = cdtMappingConfig.getVisitDetailId();
    }

    public Boolean validateRequiredOptionalsNotNull() {
        return statusOptional.validate(jpaEntity)
                && endTimeOptional.validate(jpaEntity)
                && stopReasonOptional.validate(jpaEntity)
                && providerOptional.validate(jpaEntity)
                && visitDetailOptional.validate(jpaEntity);
    }

    @Override
    public void setPersonAndType(Person person, Concept type) {
        jpaEntity.setPerson(person);
        jpaEntity.setConditionTypeConcept(type);
    }

    @Override
    public void setStandardConcept(Optional<Concept> conceptCode) {
        populateFieldIfPresent(conceptCode, conceptValue -> jpaEntity.setConditionConcept(conceptValue));
    }

    @Override
    public void setSourceConcept(Optional<Concept> sourceConcept) {
        populateFieldIfPresent(sourceConcept, sourceConceptValue -> jpaEntity.setConditionSourceConcept(sourceConceptValue));
    }

    @Override
    public void setEntitySourceValue(Optional<String> entitySourceValue) {
        populateFieldIfPresent(entitySourceValue, entitySourceValueValue -> jpaEntity.setConditionSourceValue(entitySourceValueValue));
    }

    @Override
    public void setDateTime(Optional<Date> startDate) {
        populateFieldIfPresent(startDate, startDateValue -> {
            jpaEntity.setConditionStartDate(startDateValue);
            jpaEntity.setConditionStartDateTime(startDateValue);
        });
    }

    public void setEndDateTime(Optional<Date> endDate) {
        populateFieldIfPresent(endDate, endTimeOptional.isOptional(), endDateValue -> {
            jpaEntity.setConditionEndDate(endDateValue);
            jpaEntity.setConditionEndDateTime(endDateValue);
        });
    }

    public void setStopReason(Optional<String> stopReason){
        populateFieldIfPresent(stopReason, stopReasonOptional.isOptional(), stopReasonValue -> {
            jpaEntity.setStopReason(stopReasonValue);
        });
    }

    public void setStatus(Optional<Concept> status, Optional<String> sourceValue) {
        populateFieldIfPresent(status, statusOptional.isOptional(), statusValue -> jpaEntity.setConditionStatusConcept(statusValue));
        populateFieldIfPresent(sourceValue, statusOptional.isOptional(), sourceValueValue -> jpaEntity.setConditionStatusSourceValue(sourceValueValue));
    }

    public void setVisitOccurrence(VisitOccurrence visitOccurrence) {
        jpaEntity.setVisitOccurrence(visitOccurrence);
    }
}
