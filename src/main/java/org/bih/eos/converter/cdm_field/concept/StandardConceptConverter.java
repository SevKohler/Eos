package org.bih.eos.converter.cdm_field.concept;

import com.nedap.archie.rm.datavalues.DataValue;
import org.bih.eos.converter.cdm_field.CDMConverter;
import org.bih.eos.jpabase.dba.service.ConceptService;
import org.bih.eos.jpabase.model.entity.Concept;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

public class StandardConceptConverter extends CDMConverter<Concept> {

    private static final Logger LOG = LoggerFactory.getLogger(StandardConceptConverter.class);
    private final ConceptService conceptService;
    private final DVTextCodeToConceptConverter conceptConverter;

    public StandardConceptConverter(ConceptService conceptService, DVTextCodeToConceptConverter conceptConverter) {
        super(StandardConceptConverter.class);
        this.conceptService = conceptService;
        this.conceptConverter = conceptConverter;
    }

    @Override
    protected Optional<Concept> convertDvValue(DataValue value) {
        return conceptConverter.convertStandardConcept(value);
    }

    @Override
    protected Optional<Concept> convertCode(Long code) {
        Concept codeConcept =  conceptService.findById(code);
        if(codeConcept == null){
            return Optional.empty();
        }else{
            return Optional.of(codeConcept);
        }
    }

}