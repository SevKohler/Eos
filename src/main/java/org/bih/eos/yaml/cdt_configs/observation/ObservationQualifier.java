package org.bih.eos.yaml.cdt_configs.observation;

import org.bih.eos.jpabase.entity.Observation;
import org.bih.eos.yaml.OptionalCdmField;

public class ObservationQualifier extends OptionalCdmField<Observation> {

    @Override
    public boolean validateInternal(Observation jpaEntity) {
        return jpaEntity.getQualifierSourceValue() != null;
    }
}

