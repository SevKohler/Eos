package org.bih.eos.converter.cdt.converter.nonconfigurable;

import com.nedap.archie.rm.composition.*;
import com.nedap.archie.rm.datastructures.Event;
import com.nedap.archie.rm.datastructures.ItemStructure;
import org.bih.eos.converter.cdt.converter.CDTConverter;
import org.bih.eos.converter.cdt.DefaultConverterServices;
import org.bih.eos.converter.cdt.conversion_entities.VisitOccurrenceEntity;
import org.bih.eos.converter.cdm_field.date.DVToDateConverter;
import org.bih.eos.jpabase.model.entity.Concept;
import org.bih.eos.jpabase.model.entity.JPABaseEntity;
import org.bih.eos.jpabase.model.entity.Person;
import org.bih.eos.jpabase.model.entity.VisitOccurrence;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;


public class VisitOccurrenceConverter extends CDTConverter {

    private static final Logger LOG = LoggerFactory.getLogger(VisitOccurrenceConverter.class);

    public VisitOccurrenceConverter(DefaultConverterServices defaultConverterServices) {
        super(defaultConverterServices);
    }

    public Optional<JPABaseEntity> convert(Composition composition, Person person) {
        VisitOccurrenceEntity visitOccurrence = new VisitOccurrenceEntity(new VisitOccurrence(), person, defaultConverterServices.getConceptService().findById(32817L));
        convertStandardAndSourceConcept(visitOccurrence);
        visitOccurrence.setDateTime(convertStartDate(composition));
        visitOccurrence.setEndDate(convertEndDate(composition));
        visitOccurrence.setAdmittedFromConcept(defaultConverterServices.getConceptService().findById(0L));
        visitOccurrence.setDischargeToConcept(defaultConverterServices.getConceptService().findById(0L));
        return visitOccurrence.toJpaEntity();
    }

    private Optional<Date> convertEndDate(Composition composition) {
        ArrayList<Optional<Date>> dates = new ArrayList<>();
        if (composition.getContext() != null && composition.getContext().getStartTime() != null) {
            return new DVToDateConverter().convertEndTime(composition.getContext());
        } else {
            for (ContentItem content : composition.getContent()) {
                dates.add(new DVToDateConverter().convertEndTime(content));
                if (content.getClass().equals(Observation.class)) {
                    getEventEndTime(content, dates);
                }
            }
            return calculateVisitEnd(dates);
        }
    }

    private Optional<Date> convertStartDate(Composition composition) {
        ArrayList<Optional<Date>> dates = new ArrayList<>();
        if (composition.getContext() != null && composition.getContext().getStartTime() != null) {
            return new DVToDateConverter().convert(composition.getContext());
        } else {
            for (ContentItem content : composition.getContent()) {
                dates.add(new DVToDateConverter().convert(content));
                if (content.getClass().equals(Observation.class)) {
                    getEventsStartTime(content, dates);
                }
            }
            return calculateVisitStart(dates);
        }
    }

    //TODO implement for Endtime !

    private Optional<Date> getEventsStartTime(ContentItem content, ArrayList<Optional<Date>> dates) {
        Observation observation = (Observation) content;
        List<Event<ItemStructure>> events = observation.getData().getEvents();
        for (Event<ItemStructure> event : events) {
            dates.add(new DVToDateConverter().convert(event));
        }
        return Optional.empty();
    }

    private Optional<Date> getEventEndTime(ContentItem content, ArrayList<Optional<Date>> dates) {
        Observation observation = (Observation) content;
        List<Event<ItemStructure>> events = observation.getData().getEvents();
        for (Event<ItemStructure> event : events) {
            dates.add(new DVToDateConverter().convertEndTime(event));
        }
        return Optional.empty();
    }


    private Optional<Date> calculateVisitEnd(ArrayList<Optional<Date>> dates) {
        Optional<Date> visitDate = getFirstValue(dates);
        if (visitDate.isPresent()) {
            for (Optional<Date> date : dates) {
                if (date.isPresent() && visitDate.get().before(date.get())) {
                    visitDate = date;
                }
            }
            return visitDate;
        } else {
            return Optional.empty();
        }
    }

    private Optional<Date> calculateVisitStart(ArrayList<Optional<Date>> dates) {
        Optional<Date> visitDate = getFirstValue(dates);
        if (visitDate.isPresent()) {
            for (Optional<Date> date : dates) {
                if (date.isPresent() && visitDate.get().after(date.get())) {
                    visitDate = date;
                }
            }
            return visitDate;
        } else {
            return Optional.empty();
        }
    }

    private Optional<Date> getFirstValue(ArrayList<Optional<Date>> dates) {
        for (Optional<Date> date : dates) {
            if (date.isPresent()) {
                return date;
            }
        }
        return Optional.empty();
    }

    private void convertStandardAndSourceConcept(VisitOccurrenceEntity visitOccurrence) {
        Concept concept = defaultConverterServices.getConceptService().findById(9201L);
        if (concept != null) {
            visitOccurrence.setStandardConcept(Optional.of(concept));
        } else {
            visitOccurrence.setStandardConcept(Optional.empty());
        }
    }

    protected JPABaseEntity persist(JPABaseEntity convertedEntity) {
        return defaultConverterServices.getPersistenceService().create(convertedEntity);
    }
}
