package org.bih.eos.yaml;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
@JsonIgnoreProperties(ignoreUnknown = true)
class OpenEhrConfig {
    @JsonProperty("archetype")
    private String archetype;

    public String getArchetype() {
        return archetype;
    }

    private void setArchetype(String archetype) {
        this.archetype = archetype;
    }
}