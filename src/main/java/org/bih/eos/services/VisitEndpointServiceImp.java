package org.bih.eos.services;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import org.bih.eos.config.VisitConverterProperties;
import org.bih.eos.controller.dao.RegistryKey;
import org.bih.eos.exceptions.UnprocessableEntityException;
import org.bih.eos.jpabase.entity.PersonVisit;
import org.bih.eos.jpabase.service.ConceptService;
import org.ehrbase.client.aql.query.NativeQuery;
import org.ehrbase.client.aql.query.Query;
import org.ehrbase.client.aql.record.Record;
import org.ehrbase.client.aql.record.Record4;
import org.ehrbase.client.openehrclient.OpenEhrClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import jakarta.transaction.Transactional;

@Repository
@Transactional
public class VisitEndpointServiceImp implements VisitEndpointService {
    private static final Logger LOG = LoggerFactory.getLogger(VisitEndpointServiceImp.class);
    private final OpenEhrClient openEhrClient;

    private final ConceptService conceptService;
//    private final String aqlPredefined="SELECT e/ehr_id/value as ehr_id,"
//    		+ " c/context/other_context/items[openEHR-EHR-CLUSTER.case_identification.v0]/items[at0001]/value/value as source_id,"
//    		+ " c/content[openEHR-EHR-ADMIN_ENTRY.hospitalization.v0]/data[at0001]/items[at0004]/value/value as begin,"
//    		+ " c/content[openEHR-EHR-ADMIN_ENTRY.hospitalization.v0]/data[at0001]/items[at0005]/value/value as end"
//    		+ " FROM EHR e CONTAINS COMPOSITION c"
//    		+ " WHERE c/archetype_details/template_id/value='Patientenaufenthalt'";
	private VisitConverterProperties visitConverterProperties;
    
    public VisitEndpointServiceImp(ConceptService conceptService, OpenEhrClient openEhrClient, VisitConverterProperties visitConverterProperties) {
    	this.conceptService=conceptService;
        this.openEhrClient = openEhrClient;
        this.visitConverterProperties=visitConverterProperties;
    }

    
    //TODO: if end null end=start
    //TODO: configurable
    //TODO: AQL end composition? example to see if possible
    //TODO: JPA
    //TODO: TEST!
    @Override
	public List<PersonVisit> findAll() {
    	List<Record4<String, String, Instant, Instant>> records = executeAqlQuery();
    	if(records.size()==0) {
    		throw new UnprocessableEntityException("Visit AQL returned no visits, please review the AQL query");
    	}
    	
//    	for (Record r : records) {
//    	    System.out.println("Record: " + r);
//    	    System.out.println("Fields: " + Arrays.toString(r.fields()));
//    	    System.out.println("Values: " + Arrays.toString(r.values()));
//    	}
    	Map<RegistryKey, List<Record>> grouped = records.stream()
    			.collect(Collectors.groupingBy(r -> new RegistryKey((String)r.value(0), (String)r.value(1))));

    	List<PersonVisit> resultPersonVisit = new ArrayList<>();

    	for (Entry<RegistryKey, List<Record>> entry : grouped.entrySet()) {
    		RegistryKey key = entry.getKey();
    		List<Record> group = entry.getValue();

    		Date minStartDate = null;
    		Date maxEndDate = null;

    		for (Record r : group) {
    			Date startDate = Date.from((Instant) r.value(2));
    			Date endDate = Date.from((Instant) r.value(3));
    			if (startDate != null && (minStartDate == null || startDate.before(minStartDate))) {
    				minStartDate = startDate;
    			}
    			if (endDate != null && (maxEndDate == null || endDate.after(maxEndDate))) {
    				maxEndDate = endDate;
    			}
    		}

    		resultPersonVisit.add(createVisit(key.getEhrId(), key.getSourceId(), minStartDate, maxEndDate));
    	}
    	return resultPersonVisit;
    }



    private List<Record4<String, String, Instant, Instant>> executeAqlQuery() {
        try {
            NativeQuery<Record4<String, String, Instant, Instant>> buildNativeQuery=null;
            List<Record4<String, String, Instant, Instant>> execute;
            buildNativeQuery = Query.buildNativeQuery(visitConverterProperties.getAql(), String.class,String.class,Instant.class,Instant.class);
			try {
				 execute = openEhrClient.aqlEndpoint().execute(buildNativeQuery);
			} catch (IndexOutOfBoundsException e) {
				throw new UnprocessableEntityException("Visit AQL has less than 4 needed parameters, namely ehr_id, source_id, begin, end");
			}
			catch (Exception e) {
				throw new UnprocessableEntityException("There was a problem processing visit AQL. AQL needs to return 4 parameters, namely ehr_id, source_id, begin, end");
			}
			return execute;
        } catch (NullPointerException nullPointerException) {
            return new ArrayList<>();
        }
    }

    private PersonVisit createVisit(String ehrId, String sourceId, Date minStartDate, Date maxEndDate) {
    	PersonVisit pv = new PersonVisit();
    	pv.setEhrId(ehrId);
    	pv.setSourceVisit(sourceId);
    	pv.setVisitStartDate(minStartDate);
    	pv.setVisitEndDate(maxEndDate);
    	return pv;
    }



    
}
