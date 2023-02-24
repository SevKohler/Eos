package org.bih.eos.converter.cdt.conversion_entities;

import org.bih.eos.jpabase.model.entity.Concept;
import org.bih.eos.jpabase.model.entity.Measurement;
import org.bih.eos.jpabase.model.entity.Person;
import org.bih.eos.jpabase.model.entity.VisitOccurrence;
import org.bih.eos.yaml.cdt_configs.measurement.*;

import java.util.Date;
import java.util.Optional;
import java.util.OptionalDouble;

public class MeasurementEntity extends EntityWithSourceConcept<Measurement> {

    private final MeasurementUnit unitOptional;
    private final MeasurementValue valueOptional;
    private final MeasurementProvider providerOptional;
    private final MeasurementVisitDetail visitDetailOptional;
    private final MeasurementEvent eventOptional;
    private final MeasurementEventConcept eventConceptOptional;
    private final MeasurementRangeLow rangeLowOptional;
    private final MeasurementRangeHigh rangeHighOptional;
    private final MeasurementOperator operatorOptional;

    public MeasurementEntity(MeasurementConfig measurementConfig, Person person, Concept type) {
        super(new Measurement(), person, type);
        this.valueOptional = measurementConfig.getValue();
        this.unitOptional = measurementConfig.getUnit();
        this.providerOptional = measurementConfig.getProviderId();
        this.visitDetailOptional = measurementConfig.getVisitDetailId();
        this.eventOptional = measurementConfig.getMeasurementEventId();
        this.eventConceptOptional = measurementConfig.getMeasEventFieldConceptId();
        this.rangeLowOptional = measurementConfig.getRangeLow();
        this.rangeHighOptional = measurementConfig.getRangeHigh();
        this.operatorOptional = measurementConfig.getOperatorConceptId();
    }

    public Boolean validateRequiredOptionalsNotNull() {
        return unitOptional.validate(jpaEntity)
                && valueOptional.validate(jpaEntity)
                && providerOptional.validate(jpaEntity)
                && visitDetailOptional.validate(jpaEntity)
                && eventOptional.validate(jpaEntity)
                && eventConceptOptional.validate(jpaEntity)
                && rangeHighOptional.validate(jpaEntity)
                && rangeLowOptional.validate(jpaEntity)
                && operatorOptional.validate(jpaEntity);
    }


    @Override
    public void setStandardConcept(Optional<Concept> conceptCode) {
        populateFieldIfPresent(conceptCode, conceptValue -> jpaEntity.setMeasurementConcept(conceptValue));
    }

    @Override
    public void setSourceConcept(Optional<Concept> sourceConcept) {
        populateFieldIfPresent(sourceConcept, conceptValue -> jpaEntity.setMeasurementSourceConcept(conceptValue));
    }

    public void setEntitySourceValue(Optional<String> entitySourceValue) {
        populateFieldIfPresent(entitySourceValue, entitySourceValueValue -> jpaEntity.setMeasurementSourceValue(entitySourceValueValue));
    }

    @Override
    public void setDateTime(Optional<Date> dateTime) {
        populateFieldIfPresent(dateTime, dateTimeValue -> {
            jpaEntity.setMeasurementDate(dateTimeValue);
            jpaEntity.setMeasurementDateTime(dateTimeValue);
        });
    }

    @Override
    public void setPersonAndType(Person person, Concept type) {
        jpaEntity.setPerson(person);
        jpaEntity.setTypeConcept(type);
    }

    public void setValue(OptionalDouble simpleMagnitude) {
        populateFieldIfPresent(convertToOptionalDouble(simpleMagnitude), valueOptional.isOptional(), magnitudeValue -> {
            jpaEntity.setValueAsNumber(magnitudeValue);
            jpaEntity.setValueSourceValue("" + magnitudeValue);
        });
    }

    public void setValue(Optional<Concept> value, Optional<String> sourceCode) {
        populateFieldIfPresent(value, valueOptional.isOptional(), conceptValue -> jpaEntity.setValueAsConcept(conceptValue));
        populateFieldIfPresent(sourceCode, valueOptional.isOptional(), sourceCodeValue -> jpaEntity.setValueSourceValue(sourceCodeValue));
    }

    public void setValue(Optional<String> value) {
        populateFieldIfPresent(value, valueOptional.isOptional(), valueValue -> jpaEntity.setValueSourceValue(valueValue));
    }

    public void setUnitConcept(Optional<Concept> unitStandardConcept, Optional<Concept> unitSourceConcept, Optional<String> unitSource) {
        populateFieldIfPresent(unitStandardConcept, unitOptional.isOptional(), unitStandardConceptValue -> jpaEntity.setUnitConcept(unitStandardConceptValue));
        populateFieldIfPresent(unitSourceConcept, unitOptional.isOptional(), unitSourceConceptValue -> jpaEntity.setUnitSourceConcept(unitSourceConceptValue));
        populateFieldIfPresent(unitSource, unitOptional.isOptional(), unitSourceValue -> jpaEntity.setUnitSourceValue(unitSourceValue));
    }

        public void setVisitOccurrence(VisitOccurrence visitOccurrence) {
        jpaEntity.setVisitOccurrence(visitOccurrence);
    }

    public void setOperator(Optional<Concept> concept){
        populateFieldIfPresent(concept, operatorOptional.isOptional(), conceptCode -> jpaEntity.setOperationConcept(conceptCode));
    }

    public void setRangeLow(Optional<Double> rangeLow){
        populateFieldIfPresent(rangeLow, rangeLowOptional.isOptional(), range -> jpaEntity.setRangeLow(range));
    }

    public void setRangeHigh(Optional<Double>  rangeHigh){
        populateFieldIfPresent(rangeHigh, rangeHighOptional.isOptional(), range -> jpaEntity.setRangeHigh(range));
    }


}
