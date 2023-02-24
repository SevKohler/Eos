package org.bih.eos.converter.cdt.conversion_entities;

import org.bih.eos.jpabase.model.entity.Concept;
import org.bih.eos.jpabase.model.entity.Observation;
import org.bih.eos.jpabase.model.entity.Person;
import org.bih.eos.jpabase.model.entity.VisitOccurrence;
import org.bih.eos.yaml.cdt_configs.observation.*;

import java.util.Date;
import java.util.Optional;
import java.util.OptionalDouble;

public class ObservationEntity extends EntityWithSourceConcept<Observation> {

    private final ObservationUnit unitOptional;
    private final ObservationValue valueOptional;
    private final ObservationQualifier qualifierOptional;
    private final ObservationProvider providerIdOptional ;
    private final ObservationVisitDetail visitDetailIdOptional;
    private final ObservationEventId eventIdOptional;
    private final ObservationEventFieldConceptId obsEventFieldConceptIdOptional;

    public ObservationEntity(ObservationConfig observationConfig, Person person, Concept type) {
        super(new Observation(), person, type);
        this.unitOptional = observationConfig.getUnit();
        this.valueOptional = observationConfig.getValue();
        this.qualifierOptional = observationConfig.getQualifier();
        this.providerIdOptional = observationConfig.getProviderId();
        this.visitDetailIdOptional = observationConfig.getVisitDetailId();
        this.eventIdOptional = observationConfig.getObservationEventId();
        this.obsEventFieldConceptIdOptional = observationConfig.getObsEventFieldConceptId();
    }

    public Boolean validateRequiredOptionalsNotNull() {
        return unitOptional.validate(jpaEntity) && valueOptional.validate(jpaEntity);
    }

    @Override
    public void setPersonAndType(Person person, Concept type) {
        jpaEntity.setPerson(person);
        jpaEntity.setObservationTypeConcept(type);
    }

    @Override
    public void setStandardConcept(Optional<Concept> conceptCode) {
        populateFieldIfPresent(conceptCode, entitySourceValueValue -> jpaEntity.setObservationConcept(entitySourceValueValue));
    }

    @Override
    public void setSourceConcept(Optional<Concept> sourceConcept) {
        populateFieldIfPresent(sourceConcept, entitySourceValueValue -> jpaEntity.setObservationSourceConcept(entitySourceValueValue));
    }

    @Override
    public void setEntitySourceValue(Optional<String> entitySourceValue) {
        populateFieldIfPresent(entitySourceValue, entitySourceValueValue -> jpaEntity.setObservationSourceValue(entitySourceValueValue));
    }

    @Override
    public void setDateTime(Optional<Date> date) {
        populateFieldIfPresent(date, dateTimeValue -> {
            jpaEntity.setObservationDate(dateTimeValue);
            jpaEntity.setObservationDateTime(dateTimeValue);
        });
    }

    public void setUnitConcept(Optional<Concept> concept) {
        populateFieldIfPresent(concept, unitOptional.isOptional(), unitValue -> jpaEntity.setUnitConcept(unitValue));
    }

    public void setUnitSourceValue(Optional<String> unitSourceValue) {
        populateFieldIfPresent(unitSourceValue, unitOptional.isOptional(), unitSourceValueValue -> jpaEntity.setUnitSourceValue( unitSourceValueValue));
    }

    public void setValue(OptionalDouble simpleMagnitude) {
        Optional<Double> magnitude = convertToOptionalDouble(simpleMagnitude);
        populateFieldIfPresent(magnitude, valueOptional.isOptional(), magnitudeValue -> {
            jpaEntity.setValueAsNumber(magnitudeValue);
            jpaEntity.setValueSourceValue(magnitudeValue.toString());
        });
    }

    public void setValue(Optional<String> value) {
        populateFieldIfPresent(value, valueOptional.isOptional(), valueValue -> jpaEntity.setValueAsString(valueValue));
        populateFieldIfPresent(value, valueOptional.isOptional(), valueValue -> jpaEntity.setValueSourceValue(valueValue));
    }

    public void setValue(Optional<Concept> concept, Optional<String> sourceValue) {
        populateFieldIfPresent(concept, valueOptional.isOptional(), conceptValue -> jpaEntity.setValueAsConcept(conceptValue));
        populateFieldIfPresent(sourceValue, valueOptional.isOptional(), sourceCodeValue -> jpaEntity.setValueSourceValue(sourceCodeValue));
    }

    public void setVisitOccurrence(VisitOccurrence visitOccurrence) {
        jpaEntity.setVisitOccurrence(visitOccurrence);
    }

    public void setQualifier(Optional<Concept> concept, Optional<String> sourceValue){
        populateFieldIfPresent(concept, qualifierOptional.isOptional(), conceptValue -> jpaEntity.setQualifierConcept(conceptValue));
        populateFieldIfPresent(sourceValue, qualifierOptional.isOptional(), sourceCodeValue -> jpaEntity.setQualifierSourceValue(sourceCodeValue));
    }
}
