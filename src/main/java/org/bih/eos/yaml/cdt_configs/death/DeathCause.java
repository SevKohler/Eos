package org.bih.eos.yaml.cdt_configs.death;

import org.bih.eos.jpabase.entity.Death;
import org.bih.eos.yaml.OptionalCdmField;

public class DeathCause extends OptionalCdmField<Death> {
    @Override
    public boolean validateInternal(Death jpaEntity) {
        return jpaEntity.getCauseConceptId() != null;
    }
}
