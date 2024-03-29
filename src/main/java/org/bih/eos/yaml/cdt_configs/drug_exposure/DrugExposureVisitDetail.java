package org.bih.eos.yaml.cdt_configs.drug_exposure;

import org.bih.eos.jpabase.entity.DrugExposure;
import org.bih.eos.yaml.OptionalCdmField;

public class DrugExposureVisitDetail extends OptionalCdmField<DrugExposure> {
    @Override
    public boolean validateInternal(DrugExposure jpaEntity) {
        return jpaEntity.getVisitDetail() != null;
    }
}