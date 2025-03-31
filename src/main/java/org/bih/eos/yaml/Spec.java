package org.bih.eos.yaml;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
@JsonIgnoreProperties(ignoreUnknown = true)
class Spec {
    private String system;
    private String version;

    @JsonProperty("openEhrConfig")
    private OpenEhrConfig openEhrConfig;

    public OpenEhrConfig getOpenEhrConfig() {
        return openEhrConfig;
    }

    private void setOpenEhrConfig(OpenEhrConfig openEhrConfig) {
        this.openEhrConfig = openEhrConfig;
    }
}
