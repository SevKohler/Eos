package org.bih.eos.yaml.cdt_configs.observation;

import org.bih.eos.jpabase.model.entity.Observation;
import org.bih.eos.yaml.OptionalCdmField;

public class ObservationEventId extends OptionalCdmField<Observation> {

    @Override
    public boolean validateInternal(Observation jpaEntity) {
        return jpaEntity.getObservationEventId() != null;
    }
}