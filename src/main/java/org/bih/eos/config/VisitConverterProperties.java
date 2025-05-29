package org.bih.eos.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "eos.visit-conversion")
public class VisitConverterProperties {
	
	private String aql;

	public String getAql() {
		return aql;
	}

	public void setAql(String aql) {
		this.aql = aql;
	}
	
}
