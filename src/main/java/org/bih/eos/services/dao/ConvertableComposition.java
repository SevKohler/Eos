package org.bih.eos.services.dao;

import com.nedap.archie.rm.composition.Composition;
import org.bih.eos.jpabase.entity.EHRToPerson;
import org.bih.eos.jpabase.entity.Person;
import org.bih.eos.jpabase.entity.VisitOccurrence;

public class ConvertableComposition {
    private final Composition composition;
    private final Person person;
    private EHRToPerson ehrToPerson;
    private VisitOccurrence visit;

    public ConvertableComposition(Composition composition, EHRToPerson ehrToPerson) {
        this.composition = composition;
        this.setEhrToPerson(ehrToPerson);
        this.person = ehrToPerson.getPerson();
    }
    
    public ConvertableComposition(Composition composition,  EHRToPerson ehrToPerson, VisitOccurrence visit) {
        this.composition = composition;
        this.setEhrToPerson(ehrToPerson);
        this.person = ehrToPerson.getPerson();
        this.visit=visit;
    }    
    
    public ConvertableComposition(Composition composition, Person person) {
        this.composition = composition;
        this.person = person;
    }
    
    public ConvertableComposition(Composition composition, Person person, VisitOccurrence visit) {
        this.composition = composition;
        this.person = person;
        this.visit=visit;
    }

    public Composition getComposition() {
        return composition;
    }

    public Person getPerson() {
        return person;
    }
    
    public VisitOccurrence getVisit() {
    	return visit;
    }
    
    public void setVisit(VisitOccurrence visit) {
    	this.visit=visit;
    }

	public EHRToPerson getEhrToPerson() {
		return ehrToPerson;
	}

	public void setEhrToPerson(EHRToPerson ehrToPerson) {
		this.ehrToPerson = ehrToPerson;
	}
}
