package org.bih.eos.yaml.cdt_configs.measurement;

import org.bih.eos.jpabase.entity.Measurement;
import org.bih.eos.yaml.OptionalCdmField;

public class MeasurementOperator extends OptionalCdmField<Measurement> {

    @Override
    public boolean validateInternal(Measurement jpaEntity) {
        return jpaEntity.getOperatorConcept() != null;
    }
}
