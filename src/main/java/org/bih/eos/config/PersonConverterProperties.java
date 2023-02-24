package org.bih.eos.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "omop-bridge.person-conversion")
public class PersonConverterProperties {

    private Mode mode = Mode.AUTOMATIC;

    public Mode getMode() {
        return mode;
    }

    public void setMode(Mode mode) {
        this.mode = mode;
    }

    public enum Mode {
        AUTOMATIC, CONVERSION
    }
}
