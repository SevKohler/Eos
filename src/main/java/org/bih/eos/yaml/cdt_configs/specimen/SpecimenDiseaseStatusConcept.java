package org.bih.eos.yaml.cdt_configs.specimen;

import org.bih.eos.jpabase.entity.Specimen;
import org.bih.eos.yaml.OptionalCdmField;

public class SpecimenDiseaseStatusConcept extends OptionalCdmField<Specimen> {
    @Override
    public boolean validateInternal(Specimen jpaEntity) {
        return jpaEntity.getDiseaseStatusSourceValue() != null;
    }
}
