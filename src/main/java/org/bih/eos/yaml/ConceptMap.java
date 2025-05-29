package org.bih.eos.yaml;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class ConceptMap {

    private String path;

    @JsonProperty("mapping")
    private Map<String, Long> mappings; // Stores at0044, at0045, etc.

    public ConceptMap() {
    }

    public String getPath() {
        return path;
    }

    private void setPath(String path) {
        this.path = path;
    }

    public Map<String, Long> getMappings() {
        return mappings;
    }

    private void setMappings(Map<String, Long> mappings) {
        this.mappings = mappings;
    }
}