package org.bih.eos.config;

import org.ehrbase.aql.parser.AqlToDtoParser;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "eos.visit-conversion")
public class VisitConverterProperties {
	
	private String aql;
	private String templateid;
	private String visitsource;

	public String getAql() {
		return aql;
	}

	public void setAql(String aql) {
		//validate AQL
		new AqlToDtoParser().parse(aql);
		this.aql = aql;
	}

	public String getTemplateid() {
		return templateid;
	}

	public void setTemplateid(String templateid) {
		this.templateid = templateid;
	}

	public String getVisitsource() {
		return visitsource;
	}

	public void setVisitsource(String visitsource) {
		this.visitsource = visitsource;
	}
	
	
}
