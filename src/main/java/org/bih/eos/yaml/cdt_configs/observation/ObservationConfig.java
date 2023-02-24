package org.bih.eos.yaml.cdt_configs.observation;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.bih.eos.converter.cdt.DefaultConverterServices;
import org.bih.eos.yaml.CdmFieldMapping;
import org.bih.eos.converter.cdt.converter.configurable.specific.ObservationConverter;
import org.bih.eos.yaml.cdt_configs.CDTMappingConfig;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class ObservationConfig extends CDTMappingConfig<ObservationConverter> {

    private CdmFieldMapping observationDate;
    private ObservationValue value = new ObservationValue();
    private ObservationUnit unit = new ObservationUnit();
    private ObservationQualifier qualifier = new ObservationQualifier();
    private ObservationProvider providerId = new ObservationProvider();
    private ObservationVisitDetail visitDetailId = new ObservationVisitDetail();
    private ObservationEventId observationEventId = new ObservationEventId();
    private ObservationEventFieldConceptId obsEventFieldConceptId = new ObservationEventFieldConceptId();


    public ObservationConfig(@JsonProperty(value = "concept_id", required = true) CdmFieldMapping conceptId,
                             @JsonProperty(value = "observation_date", required = true) CdmFieldMapping observationDate) {
        this.conceptId = conceptId;
        this.observationDate = observationDate;
    }

    @Override
    public ObservationConverter getConverterInstance(DefaultConverterServices defaultConverterServices) {
        return new ObservationConverter(defaultConverterServices, this);
    }

    public CdmFieldMapping getObservationDate() {
        return observationDate;
    }

    private void setObservationDate(CdmFieldMapping observationDate) {
        this.observationDate = observationDate;
    }

    public ObservationValue getValue() {
        return value;
    }

    private void setValue(ObservationValue value) {
        this.value = value;
    }

    public ObservationUnit getUnit() {
        return unit;
    }

    private void setUnit(ObservationUnit unit) {
        this.unit = unit;
    }

    public ObservationQualifier getQualifier() {
        return qualifier;
    }

    private void setQualifier(ObservationQualifier qualifier) {
        this.qualifier = qualifier;
    }

    public ObservationProvider getProviderId() {
        return providerId;
    }

    private void setProviderId(ObservationProvider providerId) {
        this.providerId = providerId;
    }

    public ObservationVisitDetail getVisitDetailId() {
        return visitDetailId;
    }

    private void setVisitDetailId(ObservationVisitDetail visitDetailId) {
        this.visitDetailId = visitDetailId;
    }

    public ObservationEventId getObservationEventId() {
        return observationEventId;
    }

    private void setObservationEventId(ObservationEventId observationEventId) {
        this.observationEventId = observationEventId;
    }

    public ObservationEventFieldConceptId getObsEventFieldConceptId() {
        return obsEventFieldConceptId;
    }

    private void setObsEventFieldConceptId(ObservationEventFieldConceptId obsEventFieldConceptId) {
        this.obsEventFieldConceptId = obsEventFieldConceptId;
    }
}

