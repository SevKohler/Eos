package org.bih.eos.yaml;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
@JsonIgnoreProperties(ignoreUnknown = true)
class OpenEhrConfig {
	@JsonProperty("archetype")
	private String archetype;

	@JsonProperty("type")
	private String type;

	@JsonProperty("engine")
	private String engine;


	public String getArchetype() {
		return archetype;
	}

	private void setArchetype(String archetype) {
		this.archetype = archetype;
	}

	public String getType() {
		return type;
	}

	private void setType(String type) {
		this.type = type;
	}

	public String getEngine() {
		return engine;
	}

	private void setEngine(String engine) {
		this.engine = engine;
	}
}