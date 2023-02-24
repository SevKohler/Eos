package org.bih.eos.converter.cdt.conversion_entities;

import org.bih.eos.jpabase.model.entity.*;
import org.bih.eos.yaml.cdt_configs.device_exposure.*;

import java.util.Date;
import java.util.Optional;
import java.util.OptionalDouble;

public class DeviceExposureEntity extends EntityWithSourceConcept<DeviceExposure> {

    private final DeviceExposureEndDate endDateOptional;
    private final DeviceExposureUniqueDeviceId uniqueDeviceIdOptional;
    private final DeviceExposureProductionId productionOptional;
    private final DeviceExposureQuantity quantityOptional;
    private final DeviceExposureProvider providerOptional;
    private final DeviceExposureUnit unitOptional;
    private final DeviceExposureVisitDetail visitDetailOptional;

    public DeviceExposureEntity(DeviceExposureConfig deviceExposureConfig, Person person, Concept type) {
        super(new DeviceExposure(), person, type);
        this.endDateOptional = deviceExposureConfig.getDeviceExposureEndDate();
        this.uniqueDeviceIdOptional = deviceExposureConfig.getUniqueDeviceId();
        this.productionOptional = deviceExposureConfig.getProductionId();
        this.quantityOptional = deviceExposureConfig.getQuantity();
        this.providerOptional = deviceExposureConfig.getProviderId();
        this.visitDetailOptional = deviceExposureConfig.getVisitDetailId();
        this.unitOptional = deviceExposureConfig.getUnit();
    }

    public Boolean validateRequiredOptionalsNotNull() {
        return endDateOptional.validate(jpaEntity)
                && uniqueDeviceIdOptional.validate(jpaEntity)
                && productionOptional.validate(jpaEntity)
                && quantityOptional.validate(jpaEntity)
                && providerOptional.validate(jpaEntity)
                && unitOptional.validate(jpaEntity)
                && visitDetailOptional.validate(jpaEntity);
    }

    @Override
    public void setPersonAndType(Person person, Concept type) {
        jpaEntity.setPerson(person);
        jpaEntity.setDeviceTypeConcept(type);
    }

    @Override
    public void setStandardConcept(Optional<Concept> conceptCode) {
        populateFieldIfPresent(conceptCode, conceptValue -> jpaEntity.setDeviceConcept(conceptValue));
    }

    @Override
    public void setSourceConcept(Optional<Concept> sourceConcept) {
        populateFieldIfPresent(sourceConcept, sourceConceptValue -> jpaEntity.setDeviceSourceConcept(sourceConceptValue));
    }

    @Override
    public void setEntitySourceValue(Optional<String> entitySourceValue) {
        populateFieldIfPresent(entitySourceValue, entitySourceValueValue -> jpaEntity.setDeviceSourceValue(entitySourceValueValue));
    }

    @Override
    public void setDateTime(Optional<Date> date) {
        populateFieldIfPresent(date, dateValue -> {
            jpaEntity.setDeviceExposureStartDate(dateValue);
            jpaEntity.setDeviceExposureStartDateTime(dateValue);
        });
    }

    public void setEndDateTime(Optional<Date> endDate) {
        populateFieldIfPresent(endDate, endDateOptional.isOptional(), endDateTime -> jpaEntity.setDeviceExposureEndDate(endDateTime));
        populateFieldIfPresent(endDate, endDateOptional.isOptional(), endDateTime -> jpaEntity.setDeviceExposureEndDateTime(endDateTime));
    }

    public void setQuantity(OptionalDouble simpleQuantity) {
        Optional<Double> quantity = convertToOptionalDouble(simpleQuantity);
        populateFieldIfPresent(quantity, quantityOptional.isOptional(), quantityValue -> jpaEntity.setQuantity(quantityValue.intValue()));
    }

    public void setUniqueDeviceId(Optional<String> device) {
        populateFieldIfPresent(device, uniqueDeviceIdOptional.isOptional(), deviceId -> jpaEntity.setUniqueDeviceId(deviceId));
    }

    public void setProdution(Optional<String> produtionId) {
        populateFieldIfPresent(produtionId, productionOptional.isOptional(), id -> jpaEntity.setProductionId(id));
    }

    public void setUnitConcept(Optional<Concept> unitStandardConcept, Optional<Concept> unitSourceConcept, Optional<String> unitSource) {
        populateFieldIfPresent(unitStandardConcept, unitOptional.isOptional(), unitStandardConceptValue -> jpaEntity.setUnitConcept(unitStandardConceptValue));
        populateFieldIfPresent(unitSourceConcept, unitOptional.isOptional(), unitSourceConceptValue -> jpaEntity.setUnitSourceConcept(unitSourceConceptValue));
        populateFieldIfPresent(unitSource, unitOptional.isOptional(), unitSourceValue -> jpaEntity.setUnitSourceValue(unitSourceValue));
    }

    public void setVisitOccurrence(VisitOccurrence visitOccurrence){
        jpaEntity.setVisitOccurrence(visitOccurrence);
    }
}
