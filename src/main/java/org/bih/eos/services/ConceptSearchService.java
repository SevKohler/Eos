package org.bih.eos.services;

import org.bih.eos.jpabase.dba.service.ConceptRelationshipService;
import org.bih.eos.jpabase.dba.service.ConceptService;
import org.bih.eos.jpabase.dba.service.ParameterWrapper;
import org.bih.eos.jpabase.model.entity.Concept;
import org.bih.eos.jpabase.model.entity.ConceptRelationship;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
//TODO optimally this implements the ConceptServiceImp and overwrites the one provided from the omop fhir ppl but this requires extending concept etc. and using that instead of their classes since i do not want to touch their code.
//TODO not a service but does what a service should do, cannot/should not be annotated why explained above

@Service
public class ConceptSearchService{
    private final ConceptRelationshipService conceptRelationshipService;
    private final ConceptService conceptService;
    private static final Logger LOG = LoggerFactory.getLogger(ConceptSearchService.class);

    public ConceptSearchService(ConceptService conceptService, ConceptRelationshipService conceptRelationshipService) {
        this.conceptRelationshipService = conceptRelationshipService;
        this.conceptService = conceptService;
    }


    public Optional<Concept> searchStandardConcept(ParameterWrapper param) {
        List<Concept> concepts = conceptService.searchWithParams(0, 0, List.of(param), null);
        if (concepts.size() == 0) {
            LOG.info("No matching concept was found, a 0 concept id is used as specified by OHDSI");
            return Optional.of(conceptService.findById(0L));
        }
        LOG.info("Queried standard concepts output: " + concepts);
        return getStandardConcept(concepts);
    }

    private Optional<Concept> getStandardConcept(List<Concept> concepts) {
        if (isStandard(concepts)) {
            return Optional.of(concepts.get(0));
        } else {
            return searchMappedStandardConcept(concepts.get(0));
        }
    }

    private boolean isStandard(List<Concept> concepts) {
        return concepts.get(0).getStandardConcept() != null && concepts.get(0).getStandardConcept().equals('S');
    }

    private Optional<Concept> searchMappedStandardConcept(Concept concept) {
        ParameterWrapper param = new ParameterWrapper(
                "String",
                Arrays.asList("id.conceptId1", "id.relationshipId"),
                Arrays.asList("=", "="),
                Arrays.asList(concept.getId().toString(), "Maps to"),
                "and"
        );
        List<ConceptRelationship> conceptRelationshipIds = conceptRelationshipService.searchWithParams(0, 0, List.of(param), null);
        LOG.info("Queried relationship concepts output: " + conceptRelationshipIds);
        if (conceptRelationshipIds.size() == 0) {
            LOG.info("No standard concept mapping was found a 0 concept id is returned as specified by OHDSI");
            return Optional.of(conceptService.findById(0l));
        }
        return Optional.of(conceptService.findById(conceptRelationshipIds.get(0).getId().getConceptId2()));
    }

    public Optional<Concept> searchSourceConcept(ParameterWrapper param) {
        List<Concept> concepts = conceptService.searchWithParams(0, 0, List.of(param), null);
        if (concepts.size() == 0) {
            LOG.info("No matching concept was found, ");
            return Optional.of(conceptService.findById(0L)); //TODO if not matching pattern was found stop mapping or return 0 ?
        }
        LOG.info("Queried source concepts output: " + concepts);
        return Optional.of(concepts.get(0));
    }


}
