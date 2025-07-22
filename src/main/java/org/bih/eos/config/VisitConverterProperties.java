package org.bih.eos.config;

import org.ehrbase.aql.parser.AqlParseException;
import org.ehrbase.aql.parser.AqlToDtoParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "eos.visit-conversion")
public class VisitConverterProperties {

	private static final Logger LOG = LoggerFactory.getLogger(VisitConverterProperties.class);
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
