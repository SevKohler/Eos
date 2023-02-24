package org.bih.eos.yaml;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

import java.util.List;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class Mapping {

    private String archetypeId;
    private List<OmopMapping> mappings;

    public Mapping() {
    }

    public String getArchetypeId() {
        return archetypeId;
    }

    private void setArchetypeId(String archetypeId) {
        this.archetypeId = archetypeId;
    }

    private void setMappings(List<OmopMapping> mappings) {
        this.mappings = mappings;
    }

    public List<OmopMapping> getMappings() {
        return mappings;
    }


}
