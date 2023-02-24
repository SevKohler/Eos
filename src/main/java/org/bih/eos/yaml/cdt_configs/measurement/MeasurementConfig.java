package org.bih.eos.yaml.cdt_configs.measurement;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.bih.eos.converter.cdt.DefaultConverterServices;
import org.bih.eos.converter.cdt.converter.configurable.specific.MeasurementConverter;
import org.bih.eos.yaml.CdmFieldMapping;
import org.bih.eos.yaml.cdt_configs.CDTMappingConfig;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class MeasurementConfig extends CDTMappingConfig<MeasurementConverter> {

    private CdmFieldMapping measurementDate;
    private MeasurementUnit unit = new MeasurementUnit();
    private MeasurementValue value = new MeasurementValue();
    private MeasurementProvider providerId = new MeasurementProvider();
    private MeasurementVisitDetail visitDetailId = new MeasurementVisitDetail();
    private MeasurementEvent measurementEventId = new MeasurementEvent();
    private MeasurementEventConcept measEventFieldConceptId = new MeasurementEventConcept();
    private MeasurementRangeLow rangeLow = new MeasurementRangeLow();
    private MeasurementRangeHigh rangeHigh = new MeasurementRangeHigh();
    private MeasurementOperator operatorConceptId = new MeasurementOperator();


    public MeasurementConfig(@JsonProperty(value = "concept_id", required = true) CdmFieldMapping conceptId,
                             @JsonProperty(value = "measurement_date", required = true) CdmFieldMapping measurementDate) {
        super("Measurement");
        this.conceptId = conceptId;
        this.measurementDate = measurementDate;
    }

    @Override
    public MeasurementConverter getConverterInstance(DefaultConverterServices defaultConverterServices) {
        return new MeasurementConverter(defaultConverterServices, this);
    }

    public CdmFieldMapping getMeasurementDate() {
        return measurementDate;
    }

    private void setMeasurementDate(CdmFieldMapping measurementDate) {
        this.measurementDate = measurementDate;
    }

    public MeasurementUnit getUnit() {
        return unit;
    }

    private void setUnit(MeasurementUnit unit) {
        this.unit = unit;
    }

    public MeasurementValue getValue() {
        return value;
    }

    private void setValue(MeasurementValue value) {
        this.value = value;
    }

    public MeasurementProvider getProviderId() {
        return providerId;
    }

    private void setProviderId(MeasurementProvider providerId) {
        this.providerId = providerId;
    }

    public MeasurementVisitDetail getVisitDetailId() {
        return visitDetailId;
    }

    private void setVisitDetailId(MeasurementVisitDetail visitDetailId) {
        this.visitDetailId = visitDetailId;
    }

    public MeasurementEvent getMeasurementEventId() {
        return measurementEventId;
    }

    private void setMeasurementEventId(MeasurementEvent measurementEventId) {
        this.measurementEventId = measurementEventId;
    }

    public MeasurementEventConcept getMeasEventFieldConceptId() {
        return measEventFieldConceptId;
    }

    private void setMeasEventFieldConceptId(MeasurementEventConcept measEventFieldConceptId) {
        this.measEventFieldConceptId = measEventFieldConceptId;
    }

    public MeasurementRangeLow getRangeLow() {
        return rangeLow;
    }

    private void setRangeLow(MeasurementRangeLow rangeLow) {
        this.rangeLow = rangeLow;
    }

    public MeasurementRangeHigh getRangeHigh() {
        return rangeHigh;
    }

    private void setRangeHigh(MeasurementRangeHigh rangeHigh) {
        this.rangeHigh = rangeHigh;
    }

    public MeasurementOperator getOperatorConceptId() {
        return operatorConceptId;
    }

    private void setOperatorConceptId(MeasurementOperator operatorConceptId) {
        this.operatorConceptId = operatorConceptId;
    }
}
