package org.bih.eos.services;

import java.util.ArrayList;
import java.util.List;

import org.bih.eos.controller.dao.Ehrs;
import org.bih.eos.jpabase.entity.PersonVisit;
import org.bih.eos.jpabase.service.EHRToPersonService;
import org.bih.eos.services.dao.ConversionResponseVisit;
import org.ehrbase.client.openehrclient.OpenEhrClient;
import org.slf4j.LoggerFactory;


public class VisitService {
    private static final org.slf4j.Logger LOG = LoggerFactory.getLogger(VisitService.class);
    private final EHRToPersonService ehrToPersonService;
    private final VisitEndpointService visitEndpointService;
    private final OpenEhrClient openEhrClient;
    private final ConverterService converterService;
    private String aql;
    int ehrBatchAmount = 40;

    public VisitService(EHRToPersonService ehrToPersonService, VisitEndpointService visitEndpointService, OpenEhrClient openEhrClient, ConverterService converterService) {
    	this.ehrToPersonService = ehrToPersonService;
    	this.visitEndpointService = visitEndpointService;
    	this.openEhrClient = openEhrClient;
    	this.converterService = converterService;
    }

    public VisitService(EHRToPersonService ehrToPersonService, VisitEndpointService visitEndpointService, OpenEhrClient openEhrClient, ConverterService converterService, String aql) {
    	this.ehrToPersonService = ehrToPersonService;
    	this.visitEndpointService = visitEndpointService;
    	this.openEhrClient = openEhrClient;
    	this.converterService = converterService;
		this.aql = aql;

	}

	public String generateAllVisits() {
    	List<PersonVisit> ehrToPersonList = visitEndpointService.findAll(aql);
    	return convertList(ehrToPersonList);
    }


    public String convertList(List<PersonVisit> personVisitList) {
    	ConversionResponseVisit output = new ConversionResponseVisit();
    	List<PersonVisit> personVisitBatch = new ArrayList<>();

    	for (PersonVisit visitPerson : personVisitList) {
    		personVisitBatch.add(visitPerson);
    		output.addPersonVisit(visitPerson);
    		if (personVisitBatch.size() == ehrBatchAmount) {
    			personVisitBatch = new ArrayList<>();
    		}
    	}
    	return output.getJson();
    }


    public String generateSpecificVisits(Ehrs ehrList) {
    	List<PersonVisit> ehrToPersonList = visitEndpointService.findEHRVisits(aql,ehrList);
    	return convertList(ehrToPersonList);
    }

}