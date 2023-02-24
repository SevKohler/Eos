package org.bih.eos.converter.cdm_field.concept;

import com.nedap.archie.rm.datavalues.DataValue;
import org.bih.eos.converter.cdm_field.CDMConverter;
import org.bih.eos.jpabase.dba.service.ConceptService;
import org.bih.eos.jpabase.model.entity.Concept;

import java.util.Optional;

public class SourceConceptConverter extends CDMConverter<Concept> {

    private final ConceptService conceptService;
    private final DVTextCodeToConceptConverter dvToConceptConverter;

    public SourceConceptConverter(ConceptService conceptService, DVTextCodeToConceptConverter dvToConceptConverter) {
        super(SourceConceptConverter.class);
        this.conceptService = conceptService;
        this.dvToConceptConverter = dvToConceptConverter;
    }

    @Override
    protected Optional<Concept> convertDvValue(DataValue value) {
       return dvToConceptConverter.convertSourceConcept(value);
    }

    @Override
    protected Optional<Concept> convertCode(Long code) {
        Concept codeConcept = conceptService.findById(code);
        if (codeConcept == null) {
            return Optional.empty();
        } else {
            return Optional.of(codeConcept);
        }
    }
}