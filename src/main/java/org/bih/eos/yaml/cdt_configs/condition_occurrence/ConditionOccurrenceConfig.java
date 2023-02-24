package org.bih.eos.yaml.cdt_configs.condition_occurrence;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.bih.eos.converter.cdt.DefaultConverterServices;
import org.bih.eos.yaml.CdmFieldMapping;
import org.bih.eos.converter.cdt.converter.configurable.specific.ConditionOccurenceConverter;
import org.bih.eos.yaml.cdt_configs.CDTMappingConfig;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class ConditionOccurrenceConfig extends CDTMappingConfig<ConditionOccurenceConverter> {

    private CdmFieldMapping conditionStartDate;
    private ConditionEndDate conditionEndDate = new ConditionEndDate();
    private ConditionStatusConcept conditionStatusConceptId = new ConditionStatusConcept();
    private ConditionStopReason stopReason = new ConditionStopReason();
    private ConditionProvider providerId = new ConditionProvider();
    private ConditionVisitDetail visitDetailId = new ConditionVisitDetail();
    //stop_reason

    @Override
    public ConditionOccurenceConverter getConverterInstance(DefaultConverterServices defaultConverterServices) {
        return new ConditionOccurenceConverter(defaultConverterServices, this);
    }

    public ConditionOccurrenceConfig(@JsonProperty(value = "concept_id", required = true) CdmFieldMapping conceptId,
                                     @JsonProperty(value = "condition_start_date", required = true) CdmFieldMapping conditionStartDate) {
        this.conceptId = conceptId;
        this.conditionStartDate = conditionStartDate;
    }

    public CdmFieldMapping getConditionStartDate() {
        return conditionStartDate;
    }

    private void setConditionStartDate(CdmFieldMapping conditionStartDate) {
        this.conditionStartDate = conditionStartDate;
    }

    public ConditionEndDate getConditionEndDate() {
        return conditionEndDate;
    }

    private void setConditionEndDate(ConditionEndDate conditionEndDate) {
        this.conditionEndDate = conditionEndDate;
    }

    public ConditionStatusConcept getConditionStatusConceptId() {
        return conditionStatusConceptId;
    }

    private void setConditionStatusConceptId(ConditionStatusConcept conditionStatusConceptId) {
        this.conditionStatusConceptId = conditionStatusConceptId;
    }

    public ConditionStopReason getStopReason() {
        return stopReason;
    }

    private void setStopReason(ConditionStopReason stopReason) {
        this.stopReason = stopReason;
    }

    public ConditionProvider getProviderId() {
        return providerId;
    }

    public void setProviderId(ConditionProvider providerId) {
        this.providerId = providerId;
    }

    public ConditionVisitDetail getVisitDetailId() {
        return visitDetailId;
    }

    public void setVisitDetailId(ConditionVisitDetail visitDetailId) {
        this.visitDetailId = visitDetailId;
    }
}

