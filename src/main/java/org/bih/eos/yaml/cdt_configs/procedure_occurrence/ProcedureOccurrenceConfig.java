package org.bih.eos.yaml.cdt_configs.procedure_occurrence;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.bih.eos.converter.cdt.DefaultConverterServices;
import org.bih.eos.converter.cdt.converter.configurable.specific.ProcedureOccurrenceConverter;
import org.bih.eos.yaml.CdmFieldMapping;
import org.bih.eos.yaml.cdt_configs.CDTMappingConfig;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class ProcedureOccurrenceConfig extends CDTMappingConfig {

    private CdmFieldMapping procedureDate;
    private ProcedureOccurrenceEndDate procedureOccurrenceEndDate = new ProcedureOccurrenceEndDate();
    private ProcedureQuantity quantity = new ProcedureQuantity();
    private ProcedureModifier modifier = new ProcedureModifier();
    private ProcedureProvider providerId = new ProcedureProvider();
    private ProcedureVisitDetail visitDetailId = new ProcedureVisitDetail();

    public ProcedureOccurrenceConfig(@JsonProperty(value = "concept_id", required = true) CdmFieldMapping conceptId,
                                     @JsonProperty(value = "procedure_start_date", required = true) CdmFieldMapping procedureDate) {
        super("Procedure");
        this.conceptId = conceptId;
        this.procedureDate = procedureDate;
    }

    @Override
    public ProcedureOccurrenceConverter getConverterInstance(DefaultConverterServices defaultConverterServices) {
        return new ProcedureOccurrenceConverter(defaultConverterServices, this);
    }

    public CdmFieldMapping getProcedureDate() {
        return procedureDate;
    }

    private void setProcedureDate(CdmFieldMapping procedureDate) {
        this.procedureDate = procedureDate;
    }

    public ProcedureOccurrenceEndDate getProcedureOccurrenceEndDate() {
        return procedureOccurrenceEndDate;
    }

    private void setProcedureOccurrenceEndDate(ProcedureOccurrenceEndDate procedureOccurrenceEndDate) {
        this.procedureOccurrenceEndDate = procedureOccurrenceEndDate;
    }

    public ProcedureQuantity getQuantity() {
        return quantity;
    }

    private void setQuantity(ProcedureQuantity quantity) {
        this.quantity = quantity;
    }

    public ProcedureModifier getModifier() {
        return modifier;
    }

    private void setModifier(ProcedureModifier modifier) {
        this.modifier = modifier;
    }

    public ProcedureProvider getProviderId() {
        return providerId;
    }

    private void setProviderId(ProcedureProvider providerId) {
        this.providerId = providerId;
    }

    public ProcedureVisitDetail getVisitDetailId() {
        return visitDetailId;
    }

    private void setVisitDetailId(ProcedureVisitDetail visitDetailId) {
        this.visitDetailId = visitDetailId;
    }
}
