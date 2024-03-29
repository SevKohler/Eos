package org.bih.eos.yaml.cdt_configs.device_exposure;

import org.bih.eos.jpabase.entity.DeviceExposure;
import org.bih.eos.yaml.OptionalCdmField;

public class DeviceExposureProvider extends OptionalCdmField<DeviceExposure> {
    @Override
    public boolean validateInternal(DeviceExposure jpaEntity) {
        return jpaEntity.getProvider() != null;
    }
}