package org.bih.eos.yaml;


import com.fasterxml.jackson.annotation.JsonAutoDetect;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class ValueEntry {

    private String path;
    private Long code;
    private ValueEntry[] multiplication;

    public ValueEntry() {
    }

    public String getPath() {
        return path;
    }

    private void setPath(String path) {
        this.path = path;
    }

    public Long getCode() {
        return code;
    }

    private void setCode(Long code) {
        this.code = code;
    }

    public ValueEntry[] getMultiplication() {
        return multiplication;
    }

    private void setMultiplication(ValueEntry[] multiplication) {
        this.multiplication = multiplication;
    }
}
