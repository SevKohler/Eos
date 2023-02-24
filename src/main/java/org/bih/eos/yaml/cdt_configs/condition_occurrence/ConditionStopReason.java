package org.bih.eos.yaml.cdt_configs.condition_occurrence;

import org.bih.eos.jpabase.model.entity.ConditionOccurrence;
import org.bih.eos.yaml.OptionalCdmField;

public class ConditionStopReason extends OptionalCdmField<ConditionOccurrence> {
    @Override
    public boolean validateInternal(ConditionOccurrence conditionOccurrence) {
        return conditionOccurrence.getStopReason() != null;
    }
}