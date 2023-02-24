package org.bih.eos.yaml.cdt_configs.specimen;

import org.bih.eos.jpabase.model.entity.Specimen;
import org.bih.eos.yaml.OptionalCdmField;

public class SpecimenUnit extends OptionalCdmField<Specimen> {
    @Override
    public boolean validateInternal(Specimen jpaEntity) {
        return jpaEntity.getUnitSourceValue() != null;
    }
}
