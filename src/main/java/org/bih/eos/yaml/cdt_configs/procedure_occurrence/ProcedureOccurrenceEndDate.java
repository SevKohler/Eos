package org.bih.eos.yaml.cdt_configs.procedure_occurrence;

import org.bih.eos.jpabase.model.entity.ProcedureOccurrence;
import org.bih.eos.yaml.OptionalCdmField;

public class ProcedureOccurrenceEndDate extends OptionalCdmField<ProcedureOccurrence> {
    @Override
    public boolean validateInternal(ProcedureOccurrence procedureOccurrence) {
        return procedureOccurrence.getProcedureEndDate() != null;
    }
}
