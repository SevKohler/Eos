package org.bih.eos.converter.cdm_field.concept;

import com.nedap.archie.rm.datavalues.DataValue;
import com.nedap.archie.rm.datavalues.DvCodedText;
import com.nedap.archie.rm.datavalues.DvText;
import org.bih.eos.converter.cdm_field.VocabularyIdConverter;
import org.bih.eos.jpabase.dba.service.ParameterWrapper;
import org.bih.eos.jpabase.model.entity.Concept;
import org.bih.eos.services.ConceptSearchService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class DVTextCodeToConceptConverter {

    final ConceptSearchService conceptSearchService;
    private static final Logger LOG = LoggerFactory.getLogger(DVTextCodeToConceptConverter.class);

    public DVTextCodeToConceptConverter(ConceptSearchService conceptSearchService) {
        this.conceptSearchService = conceptSearchService;
    }

    public Optional<Concept> convertStandardConcept(DataValue dataValue) {
        if (dataValue.getClass() == DvText.class) {
            return parseStandardConcept((DvText) dataValue);
        } else if (dataValue.getClass() == DvCodedText.class) {
            return parseStandardConcept((DvCodedText) dataValue);
        }
        LOG.info("Conversion for this field supports only DV_Text or DV_Coded_Text. Therefore nothing is mapped.");
        return Optional.empty();
    }

    public Optional<Concept> convertSourceConcept(DataValue dataValue) {
        if (dataValue.getClass() == DvText.class) {
            return parseSourceConcept((DvText) dataValue);
        } else if (dataValue.getClass() == DvCodedText.class) {
            return parseSourceConcept((DvCodedText) dataValue);
        }
        LOG.info("Conversion for this field supports only DV_Text or DV_Coded_Text. Therefore nothing is mapped.");
        return Optional.empty();
    }


    private Optional<Concept> parseStandardConcept(DvCodedText dvCodedText) {
        return conceptSearchService.searchStandardConcept(buildQuery(dvCodedText));
    }

    private Optional<Concept> parseStandardConcept(DvText dvText) {
        return conceptSearchService.searchStandardConcept(buildQuery(dvText));
    }

    private Optional<Concept> parseSourceConcept(DvCodedText dvCodedText) {
        return conceptSearchService.searchSourceConcept(buildQuery(dvCodedText));
    }

    private Optional<Concept> parseSourceConcept(DvText dvText) {
        return conceptSearchService.searchSourceConcept(buildQuery(dvText));
    }

    private ParameterWrapper buildQuery(DvText dvText) {
        return new ParameterWrapper(
                "String",
                List.of("conceptCode"),
                List.of("="),
                List.of(dvText.getValue()),
                "and"
        );
    }

    private ParameterWrapper buildQuery(DvCodedText dvCodedText) {
        String vocabularyId = VocabularyIdConverter.convert(dvCodedText.getDefiningCode().getTerminologyId().getValue());
        return new ParameterWrapper(
                "String",
                Arrays.asList("vocabularyId", "conceptCode"),
                Arrays.asList("=", "="),
                Arrays.asList(vocabularyId, dvCodedText.getDefiningCode().getCodeString()),
                "and"
        );
    }

}

