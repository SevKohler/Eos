package org.bih.eos.converter.cdt;

import org.bih.eos.converter.cdm_field.concept.DVTextCodeToConceptConverter;
import org.bih.eos.jpabase.dba.service.ConceptService;
import org.bih.eos.services.ConceptSearchService;
import org.bih.eos.services.PersistenceService;

public class DefaultConverterServices {

    private final ConceptService conceptService;
    private final ConceptSearchService conceptSearchService;
    private final DVTextCodeToConceptConverter elementToConceptConverter;
    private final PersistenceService persistenceService;

    public DefaultConverterServices(ConceptService conceptService, ConceptSearchService conceptSearchService, DVTextCodeToConceptConverter conceptConverter, PersistenceService persistenceService) {
        this.conceptService = conceptService;
        this.conceptSearchService = conceptSearchService;
        this.elementToConceptConverter = conceptConverter;
        this.persistenceService = persistenceService;
    }

    public ConceptService getConceptService() {
        return conceptService;
    }

    public DVTextCodeToConceptConverter getElementToConceptConverter() {
        return elementToConceptConverter;
    }

    public PersistenceService getPersistenceService() {
        return persistenceService;
    }

    public ConceptSearchService getConceptSearchService() {
        return conceptSearchService;
    }
}
