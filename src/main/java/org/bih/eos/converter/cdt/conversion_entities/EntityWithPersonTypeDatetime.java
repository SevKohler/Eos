package org.bih.eos.converter.cdt.conversion_entities;

import org.bih.eos.jpabase.entity.Concept;
import org.bih.eos.jpabase.entity.JPABaseEntity;
import org.bih.eos.jpabase.entity.Person;

import java.util.Date;
import java.util.Optional;

public abstract class EntityWithPersonTypeDatetime<B extends JPABaseEntity> extends Entity<B>{

    public EntityWithPersonTypeDatetime(B jpaEntity, Person person, Concept type) {
        super(jpaEntity);
        setPersonAndType(person, type);
    }

    protected abstract void setPersonAndType(Person person, Concept type);

    public abstract void setDateTime(Optional<Date> date);

}
