package org.bih.eos.yaml;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class CdmFieldMapping {

    ValueEntry[] alternatives;

    public ValueEntry[] getAlternatives() {
        return alternatives;
    }

    private void setAlternatives(ValueEntry[] alternatives) {
        this.alternatives = alternatives;
    }
}
