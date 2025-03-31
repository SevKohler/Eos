package org.bih.eos.yaml;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Mapping {

    private Spec spec;
    private List<OmopMapping> mappings;

    public Mapping() {
    }

    public String getArchetypeId() {
        return spec != null && spec.getOpenEhrConfig() != null
                ? spec.getOpenEhrConfig().getArchetype()
                : null;
    }

    public List<OmopMapping> getMappings() {
        return mappings;
    }

    private void setSpec(Spec spec) {
        this.spec = spec;
    }

    private void setMappings(List<OmopMapping> mappings) {
        this.mappings = mappings;
    }
}