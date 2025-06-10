package org.bih.eos.services;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.stream.Collectors;

import org.bih.eos.config.VisitConverterProperties;
import org.bih.eos.controller.dao.RegistryKey;
import org.bih.eos.converter.cdt.DefaultConverterServices;
import org.bih.eos.exceptions.UnprocessableEntityException;
import org.bih.eos.jpabase.entity.EHRToPerson;
import org.bih.eos.jpabase.entity.PersonVisit;
import org.bih.eos.jpabase.entity.PersonVisitId;
import org.bih.eos.jpabase.entity.VisitOccurrence;
import org.bih.eos.jpabase.jpa.dao.PersonVisitRepository;
import org.bih.eos.jpabase.service.ConceptService;
import org.bih.eos.jpabase.service.EHRToPersonService;
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
    private final PersonVisitRepository personVisitRepository;
    private final ConceptService conceptService;
    private final EHRToPersonService ehrToPersonService;
	private VisitConverterProperties visitConverterProperties;

    
    public VisitEndpointServiceImp(ConceptService conceptService, OpenEhrClient openEhrClient, VisitConverterProperties visitConverterProperties, PersonVisitRepository personVisitRepository,EHRToPersonService ehrToPersonService ) {
    	this.conceptService=conceptService;
        this.openEhrClient = openEhrClient;
        this.visitConverterProperties=visitConverterProperties;
        this.personVisitRepository=personVisitRepository;
        this.ehrToPersonService = ehrToPersonService;
    }

    
    //TODO: if end null end=start DONE
    //TODO: configurable DONE
    //TODO: AQL end composition? example to see if possible DONE
    //TODO: JPA DONE
    //TODO: TEST!
    @Override
	public List<PersonVisit> findAll() {
    	List<Record4<String, String, Instant, Instant>> records = executeAqlQuery();
    	if(records.size()==0) {
    		throw new UnprocessableEntityException("Visit AQL returned no visits, please review the AQL query");
    	}
    	
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
    			Date endDate;
    			if(r.value(3)!=null)
    			{
    				endDate = Date.from((Instant) r.value(3));
    			}
    			else //if endDate is null, assume endDate as startDate for min max date calculus
    			{
    				endDate=Date.from(startDate.toInstant());
    			}
    			
    			
    			if (startDate != null && (minStartDate == null || startDate.before(minStartDate))) {
    				minStartDate = startDate;
    			}
    			if (endDate != null && (maxEndDate == null || endDate.after(maxEndDate))) {
    				maxEndDate = endDate;
    			}
    		}

    		Optional<EHRToPerson> ehrtoperson = ehrToPersonService.findByEhrId(key.getEhrId());
    		if(ehrtoperson.isPresent())
    			resultPersonVisit.add(createVisit(key.getEhrId(), key.getSourceId(), minStartDate, maxEndDate,ehrtoperson.get()));
    	}
    	
    	saveAll(resultPersonVisit);
    	
    	return resultPersonVisit;
    }



    private void saveAll(List<PersonVisit> resultPersonVisit) {
		for(PersonVisit pv:resultPersonVisit) {
			Optional<PersonVisit> existente = personVisitRepository.findByEhrIdAndSourceVisit(pv.getEhrId(),pv.getSourceVisit());
			if (!existente.isPresent()) {
				personVisitRepository.save(pv);
			}
			else {
				System.out.println("PersonVisit already exists "+pv);
			}
			
		}
		
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

    private PersonVisit createVisit(String ehrId, String sourceId, Date minStartDate, Date maxEndDate, EHRToPerson ehrToPerson) {

    	PersonVisit pv = new PersonVisit();
       	pv.setEhrId(ehrId);
       	pv.setSourceVisit(sourceId);
    	pv.setVisitStartDate(minStartDate);
    	pv.setVisitStartDateTime(minStartDate);
    	pv.setVisitEndDate(maxEndDate);
    	pv.setVisitEndDateTime(maxEndDate);
    	VisitOccurrence vo=new VisitOccurrence();
    	vo.setVisitTypeConcept(conceptService.findById(32817L));
    	vo.setVisitConcept(conceptService.findById(9201L));
    	vo.setVisitStartDate(minStartDate);
    	vo.setVisitStartDateTime(minStartDate);
    	vo.setVisitEndDate(maxEndDate);
    	vo.setVisitEndDateTime(maxEndDate);
    	vo.setPerson(ehrToPerson.getPerson());
    	pv.setVisitOccurrence(vo);
    	return pv;
    }



    
}
