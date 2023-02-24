package org.bih.eos.converter.cdt.conversion_entities;

import org.bih.eos.jpabase.model.entity.Concept;
import org.bih.eos.jpabase.model.entity.Person;
import org.bih.eos.jpabase.model.entity.Specimen;
import org.bih.eos.yaml.cdt_configs.specimen.*;

import java.util.Date;
import java.util.Optional;
import java.util.OptionalDouble;

public class SpecimenEntity extends EntityWithStandardConcept<Specimen> {

    private final SpecimenUnit unitOptional;
    private final SpecimenQuantity quantityOptional;
    private final SpecimenAnatomicSiteConcept anatomicSiteConceptOptional;
    private final SpecimenDiseaseStatusConcept diseaseStatusConceptOptional;
    private final SpecimenSourceId specimenSourceIdOptional;

    public SpecimenEntity(SpecimenConfig specimenConfig, Person person, Concept type) {
        super(new Specimen(), person, type);
        this.unitOptional = specimenConfig.getUnit();
        this.quantityOptional = specimenConfig.getQuantity();
        this.anatomicSiteConceptOptional = specimenConfig.getAnatomicSiteConcept();
        this.diseaseStatusConceptOptional = specimenConfig.getDiseaseStatusConcept();
        this.specimenSourceIdOptional = specimenConfig.getSpecimenSourceId();
    }

    public Boolean validateRequiredOptionalsNotNull() {
      return unitOptional.validate(jpaEntity)
              && quantityOptional.validate(jpaEntity)
              && anatomicSiteConceptOptional.validate(jpaEntity)
              && diseaseStatusConceptOptional.validate(jpaEntity)
              && specimenSourceIdOptional.validate(jpaEntity);
    }

    @Override
    public void setPersonAndType(Person person, Concept type) {
        jpaEntity.setPerson(person);
        jpaEntity.setSpecimenTypeConcept(type);
    }

    @Override
    public void setStandardConcept(Optional<Concept> conceptCode) {
        populateFieldIfPresent(conceptCode, conceptValue -> jpaEntity.setSpecimenConcept(conceptValue));
    }

    @Override
    public void setEntitySourceValue(Optional<String> entitySourceValue) {
        populateFieldIfPresent(entitySourceValue, entitySourceValueValue -> jpaEntity.setSpecimenSourceValue(entitySourceValueValue));
    }

    @Override
    public void setDateTime(Optional<Date> date) {
        populateFieldIfPresent(date, dateValue -> {
            jpaEntity.setSpecimenDate(dateValue);
            jpaEntity.setSpecimenDateTime(dateValue);
        });
    }

    public void setUnit(Optional<Concept> unit, Optional<String> unitSource) {
        populateFieldIfPresent(unit, unitOptional.isOptional(), unitValue -> jpaEntity.setUnitConcept(unitValue));
        populateFieldIfPresent(unitSource, unitOptional.isOptional(), unitSourceValue -> jpaEntity.setUnitSourceValue(unitSourceValue));
    }

    public void setQuantity(OptionalDouble simpleQuantity) {
        Optional<Double> quantity = convertToOptionalDouble(simpleQuantity);
        populateFieldIfPresent(quantity, quantityOptional.isOptional(), quantityValue -> jpaEntity.setQuantity(quantityValue));
    }

    public void setAnatomicSiteConcept(Optional<Concept> concept, Optional<String> sourceValue){
        populateFieldIfPresent(concept, anatomicSiteConceptOptional.isOptional(), anatomicConcept -> {
            jpaEntity.setAnatomicSiteConcept(anatomicConcept);
        });
        populateFieldIfPresent(sourceValue, anatomicSiteConceptOptional.isOptional(), sourceValueValue -> {
            jpaEntity.setAnatomicSiteSourceValue(sourceValueValue);
        });
    }

    public void setDiseaseStatusConcept(Optional<Concept> concept, Optional<String> sourceValue){
        populateFieldIfPresent(concept, diseaseStatusConceptOptional.isOptional(), diseaseConcept -> {
            jpaEntity.setDiseaseStatusConcept(diseaseConcept);
        });
        populateFieldIfPresent(sourceValue, diseaseStatusConceptOptional.isOptional(), sourceValueValue -> {
            jpaEntity.setDiseaseStatusSourceValue(sourceValueValue);
        });
    }

    public void setSpecimenSourceId(Optional<String> specimenSourceId){
        populateFieldIfPresent(specimenSourceId, specimenSourceIdOptional.isOptional(), specimenSoure ->
        {
            jpaEntity.setSpecimenSource(specimenSoure);
        });
    }

}
