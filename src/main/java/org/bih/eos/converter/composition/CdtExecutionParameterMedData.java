package org.bih.eos.converter.composition;

import org.bih.eos.jpabase.model.entity.Person;
import org.bih.eos.jpabase.model.entity.VisitOccurrence;

import java.util.Optional;

public class CdtExecutionParameterMedData extends CdtExecutionParameter{
    Person person;
    Optional<VisitOccurrence> visitOccurrence;

    public CdtExecutionParameterMedData(Person person, Optional<VisitOccurrence> visitOccurrence) {
        this.visitOccurrence = visitOccurrence;
        this.person = person;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public Optional<VisitOccurrence> getVisitOccurrence() {
        return visitOccurrence;
    }
}