package org.bih.eos.yaml.cdt_configs.measurement;

import org.bih.eos.jpabase.model.entity.Measurement;
import org.bih.eos.yaml.OptionalCdmField;

public class MeasurementEvent extends OptionalCdmField<Measurement> {

    @Override
    public boolean validateInternal(Measurement jpaEntity) {
        return jpaEntity.getMeasurementEventId() != null;
    }
}