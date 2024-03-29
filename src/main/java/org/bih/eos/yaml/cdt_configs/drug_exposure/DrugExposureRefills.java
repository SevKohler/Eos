package org.bih.eos.yaml.cdt_configs.drug_exposure;

import org.bih.eos.jpabase.entity.DrugExposure;
import org.bih.eos.yaml.OptionalCdmField;

public class DrugExposureRefills extends OptionalCdmField<DrugExposure> {
    @Override
    public boolean validateInternal(DrugExposure jpaEntity) {
        return jpaEntity.getRefills() != null;
    }
}