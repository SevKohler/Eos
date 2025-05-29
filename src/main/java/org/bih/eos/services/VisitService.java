package org.bih.eos.services;

import java.util.ArrayList;
import java.util.List;

import org.bih.eos.controller.dao.Ehrs;
import org.bih.eos.jpabase.entity.EHRToPerson;
import org.bih.eos.jpabase.entity.PersonVisit;
import org.bih.eos.jpabase.service.EHRToPersonService;
import org.bih.eos.services.dao.ConversionResponseVisit;
import org.ehrbase.client.openehrclient.OpenEhrClient;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class VisitService {
    private static final org.slf4j.Logger LOG = LoggerFactory.getLogger(VisitService.class);
    private final EHRToPersonService ehrToPersonService;
    private final VisitEndpointService visitEndpointService;
    private final OpenEhrClient openEhrClient;
    private final ConverterService converterService;
    int ehrBatchAmount = 40;

    public VisitService(EHRToPersonService ehrToPersonService, VisitEndpointService visitEndpointService, OpenEhrClient openEhrClient, ConverterService converterService) {
        this.ehrToPersonService = ehrToPersonService;
    	this.visitEndpointService = visitEndpointService;
        this.openEhrClient = openEhrClient;
        this.converterService = converterService;
    }

    public String generateAllVisits() {
        List<PersonVisit> ehrToPersonList = visitEndpointService.findAll();
        return convertList(ehrToPersonList);
       // return "";
    }
    
    
    public String convertList(List<PersonVisit> PersonVisitList) {
    	ConversionResponseVisit output = new ConversionResponseVisit();
        List<PersonVisit> personVisitBatch = new ArrayList<>();

        for (PersonVisit visitPerson : PersonVisitList) {
        	personVisitBatch.add(visitPerson);
            output.increaseOneComposition(visitPerson.getSourceVisit());
        	System.out.println(visitPerson);
            if (personVisitBatch.size() == ehrBatchAmount) {
            	personVisitBatch = new ArrayList<>();
            }
        }
        if (personVisitBatch.size() != 0) {
            //loadCompositions(ehrToPersonBatch, output);
        }
        //converterService.convertLastBatch();
        return output.getJson();
    }


    public String generateSpecificVisits(Ehrs ehrList) {
        List<EHRToPerson> ehrToPersonList = new ArrayList<>();
//        for (String ehrId : ehrList.getEhrIds()) {
//            Optional<EHRToPerson> ehrToPersonOptional = ehrToPersonService.findByEhrId(ehrId);
//            ehrToPersonOptional.ifPresent(ehrToPersonList::add);
//        }
//        return convertList(ehrToPersonList);
        return "";
    }

}