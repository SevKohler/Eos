package org.bih.eos.converter.cdt.conversion_entities;

import org.bih.eos.jpabase.model.entity.Concept;
import org.bih.eos.jpabase.model.entity.Person;
import org.bih.eos.jpabase.model.entity.ProcedureOccurrence;
import org.bih.eos.jpabase.model.entity.VisitOccurrence;
import org.bih.eos.yaml.cdt_configs.procedure_occurrence.*;

import java.util.Date;
import java.util.Optional;
import java.util.OptionalDouble;

public class ProcedureOccurrenceEntity extends EntityWithSourceConcept<ProcedureOccurrence> {

    private final ProcedureOccurrenceEndDate endTimeOptional;
    private final ProcedureModifier modifierOptional;
    private final ProcedureProvider providerOptional;
    private final ProcedureQuantity quantityOptional;
    private final ProcedureVisitDetail visitDetailOptional;


    public ProcedureOccurrenceEntity(ProcedureOccurrenceConfig procedureOccurrenceConfig, Person person, Concept type) {
        super(new ProcedureOccurrence(), person, type);
        this.endTimeOptional = procedureOccurrenceConfig.getProcedureOccurrenceEndDate();
        this.modifierOptional = procedureOccurrenceConfig.getModifier();
        this.providerOptional = procedureOccurrenceConfig.getProviderId();
        this.quantityOptional = procedureOccurrenceConfig.getQuantity();
        this.visitDetailOptional = procedureOccurrenceConfig.getVisitDetailId();
    }

    @Override
    protected Boolean validateRequiredOptionalsNotNull() {
        return endTimeOptional.validate(jpaEntity)
                && modifierOptional.validate(jpaEntity)
                && providerOptional.validate(jpaEntity)
                && quantityOptional.validate(jpaEntity)
                && visitDetailOptional.validate(jpaEntity);
    }

    @Override
    protected void setPersonAndType(Person person, Concept type) {
        jpaEntity.setPerson(person);
        jpaEntity.setProcedureTypeConcept(type);
    }

    @Override
    public void setStandardConcept(Optional<Concept> conceptCode) {
        populateFieldIfPresent(conceptCode, entitySourceValueValue -> jpaEntity.setProcedureConcept(entitySourceValueValue));
    }

    @Override
    public void setEntitySourceValue(Optional<String> entitySourceValue) {
        populateFieldIfPresent(entitySourceValue, entitySourceValueValue -> jpaEntity.setProcedureSourceValue(entitySourceValueValue));
    }

    @Override
    public void setDateTime(Optional<Date> date) {
        populateFieldIfPresent(date, dateTimeValue -> {
            jpaEntity.setProcedureDate(dateTimeValue);
            jpaEntity.setProcedureDateTime(dateTimeValue);
        });
    }


    public void setEndDateTime(Optional<Date> endDate) {
        populateFieldIfPresent(endDate, endTimeOptional.isOptional(), endDateValue -> {
            jpaEntity.setProcedureEndDate(endDateValue);
            jpaEntity.setProcedureEndDateTime(endDateValue);
        });
    }


    @Override
    public void setSourceConcept(Optional<Concept> sourceConcept) {
        populateFieldIfPresent(sourceConcept, entitySourceValueValue -> jpaEntity.setProcedureSourceConcept(entitySourceValueValue));
    }

    public void setVisitOccurrence(VisitOccurrence visitOccurrence) {
        jpaEntity.setVisitOccurrence(visitOccurrence);
    }

    public void setModifier(Optional<Concept> modifierConcept, Optional<String> modifierValue){
        populateFieldIfPresent(modifierConcept, modifierOptional.isOptional(), modifierConceptCode -> {
            jpaEntity.setModifierConcept(modifierConceptCode);
        });
        populateFieldIfPresent(modifierValue, modifierOptional.isOptional(), modifierSourceValue -> {
            jpaEntity.setModifierSourceValue(modifierSourceValue);
        });
    }

    public void setQuantity(OptionalDouble quantity) {
        populateFieldIfPresent(convertToOptionalInteger(quantity), quantityOptional.isOptional(), quantityValue -> jpaEntity.setQuantity(quantityValue));
    }

}
