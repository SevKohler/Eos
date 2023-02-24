package org.bih.eos.yaml.cdt_configs.drug_exposure;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.bih.eos.converter.cdt.DefaultConverterServices;
import org.bih.eos.yaml.CdmFieldMapping;
import org.bih.eos.converter.cdt.converter.configurable.specific.DrugExposureConverter;
import org.bih.eos.yaml.cdt_configs.CDTMappingConfig;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class DrugExposureConfig extends CDTMappingConfig<DrugExposureConverter>{

    private CdmFieldMapping drugExposureStartDate;
    private CdmFieldMapping drugExposureEndDate;
    private DrugExposureRouteConceptId routeConceptId = new DrugExposureRouteConceptId();
    private DrugExposureQuantity quantity = new DrugExposureQuantity();
    private DrugExposureVerbatimEndDate verbatimEndDate = new DrugExposureVerbatimEndDate();
    private DrugExposureStopReason stopReason = new DrugExposureStopReason();
    private DrugExposureRefills refills = new DrugExposureRefills();
    private DrugExposureDaySupply daySupply = new DrugExposureDaySupply();
    private DrugExposureSig sig = new DrugExposureSig();
    private DrugExposureLotNumber lotNumber = new DrugExposureLotNumber();
    private DrugExposureProviderId providerId = new DrugExposureProviderId();
    private DrugExposureVisitDetail visitDetailId = new DrugExposureVisitDetail();

    public DrugExposureConfig(@JsonProperty(value = "concept_id", required = true) CdmFieldMapping conceptId,
                              @JsonProperty(value = "drug_exposure_start_date", required = true) CdmFieldMapping drugExposureStartDate,
                              @JsonProperty(value = "drug_exposure_end_date", required = true) CdmFieldMapping drugExposureEndDate){
        this.conceptId = conceptId;
        this.drugExposureStartDate = drugExposureStartDate;
        this.drugExposureEndDate = drugExposureEndDate;
    }

    @Override
    public DrugExposureConverter getConverterInstance(DefaultConverterServices defaultConverterServices) {
        return new DrugExposureConverter(defaultConverterServices, this);
    }

    public CdmFieldMapping getDrugExposureStartDate() {
        return drugExposureStartDate;
    }

    private void setDrugExposureStartDate(CdmFieldMapping drugExposureStartDate) {
        this.drugExposureStartDate = drugExposureStartDate;
    }

    public CdmFieldMapping getDrugExposureEndDate() {
        return drugExposureEndDate;
    }

    private void setDrugExposureEndDate(CdmFieldMapping drugExposureEndDate) {
        this.drugExposureEndDate = drugExposureEndDate;
    }

    public DrugExposureRouteConceptId getRouteConceptId() {
        return routeConceptId;
    }

    private void setRouteConceptId(DrugExposureRouteConceptId routeConceptId) {
        this.routeConceptId = routeConceptId;
    }

    public DrugExposureQuantity getQuantity() {
        return quantity;
    }

    private void setQuantity(DrugExposureQuantity quantity) {
        this.quantity = quantity;
    }

    public DrugExposureVerbatimEndDate getVerbatimEndDate() {
        return verbatimEndDate;
    }

    private void setVerbatimEndDate(DrugExposureVerbatimEndDate verbatimEndDate) {
        this.verbatimEndDate = verbatimEndDate;
    }

    public DrugExposureStopReason getStopReason() {
        return stopReason;
    }

    private void setStopReason(DrugExposureStopReason stopReason) {
        this.stopReason = stopReason;
    }

    public DrugExposureRefills getRefills() {
        return refills;
    }

    private void setRefills(DrugExposureRefills refills) {
        this.refills = refills;
    }

    public DrugExposureDaySupply getDaySupply() {
        return daySupply;
    }

    private void setDaySupply(DrugExposureDaySupply daySupply) {
        this.daySupply = daySupply;
    }

    public DrugExposureSig getSig() {
        return sig;
    }

    private void setSig(DrugExposureSig sig) {
        this.sig = sig;
    }

    public DrugExposureLotNumber getLotNumber() {
        return lotNumber;
    }

    private void setLotNumber(DrugExposureLotNumber lotNumber) {
        this.lotNumber = lotNumber;
    }

    public DrugExposureProviderId getProviderId() {
        return providerId;
    }

    private void setProviderId(DrugExposureProviderId providerId) {
        this.providerId = providerId;
    }

    public DrugExposureVisitDetail getVisitDetailId() {
        return visitDetailId;
    }

    private void setVisitDetailId(DrugExposureVisitDetail visitDetailId) {
        this.visitDetailId = visitDetailId;
    }
}
