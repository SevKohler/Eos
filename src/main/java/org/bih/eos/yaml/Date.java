package org.bih.eos.yaml;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class Date {
    private String path;

    public Date(String path) {
        this.path = path;
    }

    public Date() {
    }

    public String getPath() {
        return path;
    }

    private void setPath(String path) {
        this.path = path;
    }
}
