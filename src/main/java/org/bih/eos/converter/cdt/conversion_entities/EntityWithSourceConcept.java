package org.bih.eos.converter.cdt.conversion_entities;

import org.bih.eos.jpabase.model.entity.Concept;
import org.bih.eos.jpabase.model.entity.JPABaseEntity;
import org.bih.eos.jpabase.model.entity.Person;

import java.util.Optional;

public abstract class EntityWithSourceConcept<B extends JPABaseEntity> extends EntityWithStandardConcept<B> {

    public EntityWithSourceConcept(B jpaEntity, Person person, Concept type) {
        super(jpaEntity, person, type);
    }

    public abstract void setSourceConcept(Optional<Concept> sourceConcept);
}
