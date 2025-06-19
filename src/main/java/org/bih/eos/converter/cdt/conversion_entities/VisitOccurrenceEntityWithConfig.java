package org.bih.eos.converter.cdt.conversion_entities;

import org.bih.eos.jpabase.entity.Concept;
import org.bih.eos.jpabase.entity.Person;
import org.bih.eos.jpabase.entity.VisitOccurrence;
import org.bih.eos.yaml.cdt_configs.visit.VisitConfig;

public class VisitOccurrenceEntityWithConfig extends VisitOccurrenceEntity {

    private final VisitConfig config;
	
	public VisitOccurrenceEntityWithConfig(VisitConfig config, Person person) {
		super(new VisitOccurrence(), person, null); // visit_type could be defined in configuration
        this.config = config;
	}

	public VisitOccurrenceEntityWithConfig(VisitConfig config, Person person, Concept type) {
		super(new VisitOccurrence(), person, type);
        this.config = config;
	}

	public VisitConfig getConfig() {
		return config;
	}

}
