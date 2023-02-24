package org.bih.eos.yaml.cdt_configs.device_exposure;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.bih.eos.converter.cdt.DefaultConverterServices;
import org.bih.eos.converter.cdt.converter.configurable.specific.DeviceExposureConverter;
import org.bih.eos.yaml.CdmFieldMapping;
import org.bih.eos.yaml.cdt_configs.CDTMappingConfig;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class DeviceExposureConfig extends CDTMappingConfig<DeviceExposureConverter> {

    private CdmFieldMapping deviceExposureStartDate;
    private DeviceExposureEndDate deviceExposureEndDate = new DeviceExposureEndDate();
    private DeviceExposureUniqueDeviceId uniqueDeviceId = new DeviceExposureUniqueDeviceId();
    private DeviceExposureProductionId productionId = new DeviceExposureProductionId();
    private DeviceExposureQuantity quantity = new DeviceExposureQuantity();
    private DeviceExposureProvider providerId = new DeviceExposureProvider();
    private DeviceExposureUnit unit = new DeviceExposureUnit();
    private DeviceExposureVisitDetail visitDetailId = new DeviceExposureVisitDetail();

    public DeviceExposureConfig(@JsonProperty(value = "concept_id", required = true) CdmFieldMapping conceptId,
                                @JsonProperty(value = "device_exposure_start_date", required = true) CdmFieldMapping drugExposureStartDate) {
        this.conceptId = conceptId;
        this.deviceExposureStartDate = drugExposureStartDate;
    }

    @Override
    public DeviceExposureConverter getConverterInstance(DefaultConverterServices defaultConverterServices) {
        return new DeviceExposureConverter(defaultConverterServices, this);
    }

    public CdmFieldMapping getDeviceExposureStartDate() {
        return deviceExposureStartDate;
    }

    public void setDeviceExposureStartDate(CdmFieldMapping deviceExposureStartDate) {
        this.deviceExposureStartDate = deviceExposureStartDate;
    }

    public DeviceExposureEndDate getDeviceExposureEndDate() {
        return deviceExposureEndDate;
    }

    public void setDeviceExposureEndDate(DeviceExposureEndDate deviceExposureEndDate) {
        this.deviceExposureEndDate = deviceExposureEndDate;
    }

    public DeviceExposureUniqueDeviceId getUniqueDeviceId() {
        return uniqueDeviceId;
    }

    public void setUniqueDeviceId(DeviceExposureUniqueDeviceId uniqueDeviceId) {
        this.uniqueDeviceId = uniqueDeviceId;
    }

    public DeviceExposureProductionId getProductionId() {
        return productionId;
    }

    public void setProductionId(DeviceExposureProductionId productionId) {
        this.productionId = productionId;
    }

    public DeviceExposureQuantity getQuantity() {
        return quantity;
    }

    public void setQuantity(DeviceExposureQuantity quantity) {
        this.quantity = quantity;
    }

    public DeviceExposureProvider getProviderId() {
        return providerId;
    }

    public void setProviderId(DeviceExposureProvider providerId) {
        this.providerId = providerId;
    }

    public DeviceExposureUnit getUnit() {
        return unit;
    }

    public void setUnit(DeviceExposureUnit unit) {
        this.unit = unit;
    }

    public DeviceExposureVisitDetail getVisitDetailId() {
        return visitDetailId;
    }

    private void setVisitDetailId(DeviceExposureVisitDetail visitDetailId) {
        this.visitDetailId = visitDetailId;
    }
}
