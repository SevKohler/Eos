package org.bih.eos.yaml.cdt_configs.device_exposure;

import org.bih.eos.jpabase.model.entity.DeviceExposure;
import org.bih.eos.yaml.OptionalCdmField;

public class DeviceExposureEndDate extends OptionalCdmField<DeviceExposure> {

    @Override
    public boolean validateInternal(DeviceExposure jpaEntity) {
        return jpaEntity.getDeviceExposureEndDate() != null;
    }

}