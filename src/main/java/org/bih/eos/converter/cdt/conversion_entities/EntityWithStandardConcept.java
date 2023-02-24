package org.bih.eos.converter.cdt.conversion_entities;

import org.bih.eos.jpabase.model.entity.JPABaseEntity;
import org.bih.eos.jpabase.model.entity.Concept;
import org.bih.eos.jpabase.model.entity.Person;

import java.util.Optional;

public abstract class EntityWithStandardConcept<B extends JPABaseEntity> extends EntityWithPersonTypeDatetime<B> {
    Boolean requiredFieldEmpty = false;
    Boolean requiredOptionalFieldNull = false;

    public EntityWithStandardConcept(B jpaEntity, Person person, Concept type) {
        super(jpaEntity, person, type);
    }

    public abstract void setStandardConcept(Optional<Concept> conceptCode);

    public abstract void setEntitySourceValue(Optional<String> entitySourceValue);

}
