package org.bih.eos.services.dao;

import com.nedap.archie.rm.composition.Composition;
import org.bih.eos.jpabase.model.entity.Person;

public class ConvertableComposition {
    private final Composition composition;
    private final Person person;

    public ConvertableComposition(Composition composition, Person person) {
        this.composition = composition;
        this.person = person;
    }

    public Composition getComposition() {
        return composition;
    }

    public Person getPerson() {
        return person;
    }
}
