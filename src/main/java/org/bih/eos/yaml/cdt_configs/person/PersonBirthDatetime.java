package org.bih.eos.yaml.cdt_configs.person;

import org.bih.eos.jpabase.model.entity.Person;
import org.bih.eos.yaml.OptionalCdmField;

public class PersonBirthDatetime extends OptionalCdmField<Person> {

    @Override
    public boolean validateInternal(Person jpaEntity) {
        return jpaEntity.getBirthDateTime() != null;
    }
}