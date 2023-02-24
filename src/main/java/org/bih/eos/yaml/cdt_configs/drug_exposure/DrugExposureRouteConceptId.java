package org.bih.eos.yaml.cdt_configs.drug_exposure;

import org.bih.eos.jpabase.model.entity.DrugExposure;
import org.bih.eos.yaml.OptionalCdmField;

public class DrugExposureRouteConceptId extends OptionalCdmField<DrugExposure> {
    @Override
    public boolean validateInternal(DrugExposure jpaEntity) {
        return jpaEntity.getRouteSourceValue() != null;
    }
}
