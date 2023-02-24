package org.bih.eos.converter.cdt.conversion_entities;

import org.bih.eos.jpabase.model.entity.Concept;
import org.bih.eos.jpabase.model.entity.Death;
import org.bih.eos.jpabase.model.entity.Person;
import org.bih.eos.yaml.cdt_configs.death.DeathCause;
import org.bih.eos.yaml.cdt_configs.death.DeathConfig;

import java.util.Date;
import java.util.Optional;

public class DeathEntity extends EntityWithPersonTypeDatetime<Death>{
    private final DeathCause deathCauseOptional;

    public DeathEntity(DeathConfig deathConfig, Person person, Concept type) {
        super(new Death(), person, type);
        this.deathCauseOptional = deathConfig.getCause();
    }

    @Override
    protected Boolean validateRequiredOptionalsNotNull() {
        return deathCauseOptional.validate(jpaEntity);
    }

    @Override
    protected void setPersonAndType(Person person, Concept type) {
        jpaEntity.setPerson(person);
        jpaEntity.setDeathTypeConceptId(type);
    }

    public void setCauseStandardConcept(Optional<Concept> causeStandardConcept) {
        populateFieldIfPresent(causeStandardConcept, deathCauseOptional.isOptional(), causeStandardConceptValue -> jpaEntity.setCauseConceptId(causeStandardConceptValue));
    }

    public void setCauseSourceConcept( Optional<Concept> causeSourceConcept) {
        populateFieldIfPresent(causeSourceConcept, deathCauseOptional.isOptional(), causeSourceConceptValue -> jpaEntity.setCauseSourceConceptId(causeSourceConceptValue));
    }

    public void setCauseSourceValue(Optional<String> causeSource) {
        populateFieldIfPresent(causeSource, deathCauseOptional.isOptional(), causeSourceValue -> jpaEntity.setCauseSourceValue(causeSourceValue));
    }

    @Override
    public void setDateTime(Optional<Date> dateTime) {
        populateFieldIfPresent(dateTime, dateTimeValue -> {
            jpaEntity.setDeathDate(dateTimeValue);
            jpaEntity.setDeathDateTime(dateTimeValue);
        });
    }


}
