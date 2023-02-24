package org.bih.eos.yaml.cdt_configs.specimen;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.bih.eos.converter.cdt.DefaultConverterServices;
import org.bih.eos.yaml.CdmFieldMapping;
import org.bih.eos.converter.cdt.converter.configurable.specific.SpecimenConverter;
import org.bih.eos.yaml.cdt_configs.CDTMappingConfig;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class SpecimenConfig extends CDTMappingConfig<SpecimenConverter> {

    private CdmFieldMapping specimenDate;
    private SpecimenUnit unit = new SpecimenUnit();
    private SpecimenQuantity quantity = new SpecimenQuantity();
    private SpecimenAnatomicSiteConcept anatomicSiteConcept = new SpecimenAnatomicSiteConcept();
    private SpecimenDiseaseStatusConcept diseaseStatusConcept = new SpecimenDiseaseStatusConcept();
    private SpecimenSourceId specimenSourceId = new SpecimenSourceId();

    public SpecimenConfig(@JsonProperty(value = "concept_id", required = true) CdmFieldMapping conceptId,
                          @JsonProperty(value = "specimen_date", required = true) CdmFieldMapping specimenDate) {
        this.conceptId = conceptId;
        this.specimenDate = specimenDate;
    }

    @Override
    public SpecimenConverter getConverterInstance(DefaultConverterServices defaultConverterServices) {
        return new SpecimenConverter(defaultConverterServices, this);
    }

    public CdmFieldMapping getSpecimenDate() {
        return specimenDate;
    }

    private void setSpecimenDate(CdmFieldMapping specimenDate) {
        this.specimenDate = specimenDate;
    }

    public SpecimenUnit getUnit() {
        return unit;
    }

    private void setUnit(SpecimenUnit unit) {
        this.unit = unit;
    }

    public SpecimenQuantity getQuantity() {
        return quantity;
    }

    private void setQuantity(SpecimenQuantity quantity) {
        this.quantity = quantity;
    }

    public SpecimenAnatomicSiteConcept getAnatomicSiteConcept() {
        return anatomicSiteConcept;
    }

    private void setAnatomicSiteConcept(SpecimenAnatomicSiteConcept anatomicSiteConcept) {
        this.anatomicSiteConcept = anatomicSiteConcept;
    }

    public SpecimenDiseaseStatusConcept getDiseaseStatusConcept() {
        return diseaseStatusConcept;
    }

    private void setDiseaseStatusConcept(SpecimenDiseaseStatusConcept diseaseStatusConcept) {
        this.diseaseStatusConcept = diseaseStatusConcept;
    }

    public SpecimenSourceId getSpecimenSourceId() {
        return specimenSourceId;
    }

    private void setSpecimenSourceId(SpecimenSourceId specimenSourceId) {
        this.specimenSourceId = specimenSourceId;
    }
}