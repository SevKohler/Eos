package org.bih.eos.yaml;


import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class ValueEntry {

    private String path;
    private Long code;
    private ValueEntry[] multiplication;
    
    @JsonProperty("conceptMap")
    private ConceptMap conceptMap;

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

	public ConceptMap getConceptMap() {
		return conceptMap;
	}

	public void setConceptMap(ConceptMap conceptMap) {
		this.conceptMap = conceptMap;
	}
    
    
   
}
