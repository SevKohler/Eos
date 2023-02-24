package org.bih.eos.converter.cdt.conversion_entities;

import org.bih.eos.jpabase.model.entity.Concept;
import org.bih.eos.jpabase.model.entity.DrugExposure;
import org.bih.eos.jpabase.model.entity.Person;
import org.bih.eos.jpabase.model.entity.VisitOccurrence;
import org.bih.eos.yaml.cdt_configs.drug_exposure.*;

import java.util.Date;
import java.util.Optional;
import java.util.OptionalDouble;

public class DrugExposureEntity extends EntityWithSourceConcept<DrugExposure> {

    private final DrugExposureRouteConceptId routeOptional;
    private final DrugExposureQuantity quantityOptional;
    private DrugExposureVerbatimEndDate verbatimEndDateOptional;
    private DrugExposureStopReason stopReasonOptional;
    private DrugExposureRefills refillsOptional;
    private DrugExposureDaySupply daySupplyOptional;
    private DrugExposureSig sigOptional;
    private DrugExposureLotNumber lotNumberOptional;
    private DrugExposureProviderId providerIdOptional;
    private DrugExposureVisitDetail visitDetailOptional;

    public DrugExposureEntity(DrugExposureConfig drugExposureConfig, Person person, Concept type) {
        super(new DrugExposure(), person, type);
        this.routeOptional = drugExposureConfig.getRouteConceptId();
        this.quantityOptional = drugExposureConfig.getQuantity();
        this.verbatimEndDateOptional = drugExposureConfig.getVerbatimEndDate();
        this.stopReasonOptional = drugExposureConfig.getStopReason();
        this.refillsOptional = drugExposureConfig.getRefills();
        this.daySupplyOptional = drugExposureConfig.getDaySupply();
        this.sigOptional = drugExposureConfig.getSig();
        this.lotNumberOptional = drugExposureConfig.getLotNumber();
        this.providerIdOptional = drugExposureConfig.getProviderId();
        this.visitDetailOptional = drugExposureConfig.getVisitDetailId();
    }

    public Boolean validateRequiredOptionalsNotNull() {
        return routeOptional.validate(jpaEntity)
                && quantityOptional.validate(jpaEntity)
                && verbatimEndDateOptional.validate(jpaEntity)
                && stopReasonOptional.validate(jpaEntity)
                && refillsOptional.validate(jpaEntity)
                && daySupplyOptional.validate(jpaEntity)
                && sigOptional.validate(jpaEntity)
                && lotNumberOptional.validate(jpaEntity)
                && providerIdOptional.validate(jpaEntity)
                && visitDetailOptional.validate(jpaEntity);
    }

    @Override
    public void setPersonAndType(Person person, Concept type) {
        jpaEntity.setPerson(person);
        jpaEntity.setDrugTypeConcept(type);
    }

    @Override
    public void setStandardConcept(Optional<Concept> conceptCode) {
        populateFieldIfPresent(conceptCode, conceptValue -> jpaEntity.setDrugConcept(conceptValue));
    }

    @Override
    public void setSourceConcept(Optional<Concept> sourceConcept) {
        populateFieldIfPresent(sourceConcept, sourceConceptValue -> jpaEntity.setDrugSourceConcept(sourceConceptValue));
    }

    @Override
    public void setEntitySourceValue(Optional<String> entitySourceValue) {
        populateFieldIfPresent(entitySourceValue, entitySourceValueValue -> jpaEntity.setDrugSourceValue(entitySourceValueValue));
    }

    @Override
    public void setDateTime(Optional<Date> date) {
        populateFieldIfPresent(date, dateValue -> {
            jpaEntity.setDrugExposureStartDate(dateValue);
            jpaEntity.setDrugExposureStartDateTime(dateValue);
        });
    }

    public void setEndDateTime(Optional<Date> endDate) {
        populateFieldIfPresent(endDate, endDateTime -> {
            jpaEntity.setDrugExposureEndDate(endDateTime);
            jpaEntity.setDrugExposureEndDateTime(endDateTime);
        });
    }

    public void setQuantity(OptionalDouble simpleQuantity) {
        Optional<Double> quantity = convertToOptionalDouble(simpleQuantity);
        populateFieldIfPresent(quantity, quantityOptional.isOptional(), quantityValue -> jpaEntity.setQuantity(quantityValue));
    }

    public void setRoute(Optional<Concept> concept, Optional<String> sourceValueRoute) {
        populateFieldIfPresent(concept, routeOptional.isOptional(), conceptValue -> jpaEntity.setRouteConcept(conceptValue));
        populateFieldIfPresent(sourceValueRoute, routeOptional.isOptional(), sourceValueRouteValue -> jpaEntity.setRouteSourceValue(sourceValueRouteValue));
    }

    public void setVisitOccurrence(VisitOccurrence visitOccurrence) {
        jpaEntity.setVisitOccurrence(visitOccurrence);
    }

    public void setVerbatimEndDate(Optional<Date> endDate) {
        populateFieldIfPresent(endDate, verbatimEndDateOptional.isOptional(), verbatimEndDateValue -> {
            jpaEntity.setVerbatimEndDate(verbatimEndDateValue);
        });
    }

    public void setStopReason(Optional<String> stopReason) {
        populateFieldIfPresent(stopReason, stopReasonOptional.isOptional(), stopReasonValue -> {
            jpaEntity.setStopReason(stopReasonValue);
        });
    }

    public void setRefills(Optional<Integer> refills) {
        populateFieldIfPresent(refills, refillsOptional.isOptional(), refillValue -> jpaEntity.setRefills(refillValue));
    }

    public void setDaySupply(Optional<Integer> daySupply) {
        populateFieldIfPresent(daySupply, daySupplyOptional.isOptional(), daySupplyValue -> jpaEntity.setDaysSupply(daySupplyValue));
    }

    public void setSig(Optional<String> sig) {
        populateFieldIfPresent(sig, sigOptional.isOptional(), sigValue -> {
            jpaEntity.setSig(sigValue);
        });
    }

    public void setLotNumber(Optional<String> lotNumber) {
        populateFieldIfPresent(lotNumber, lotNumberOptional.isOptional(), lotNumberValue -> {
            jpaEntity.setLotNumber(lotNumberValue);
        });
    }

}
