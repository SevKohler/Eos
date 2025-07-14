package org.bih.eos.services;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import org.bih.eos.config.VisitConverterProperties;
import org.bih.eos.controller.dao.Ehrs;
import org.bih.eos.controller.dao.RegistryKey;
import org.bih.eos.exceptions.UnprocessableEntityException;
import org.bih.eos.jpabase.entity.EHRToPerson;
import org.bih.eos.jpabase.entity.PersonVisit;
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


	@Override
	public List<PersonVisit> findAll(String aql) {
		List<PersonVisit> resultPersonVisit = getPersonVisitFromAQL(aql);
		saveAll(resultPersonVisit);
		return resultPersonVisit;
	}

	@Override
	public List<PersonVisit> findEHRVisits(String aql,Ehrs ehrList) {
		List<PersonVisit> resultPersonVisit = getPersonVisitFromAQL(aql);
		keepEHRs(resultPersonVisit,ehrList);
		saveAll(resultPersonVisit);
		return resultPersonVisit;
	}


	private List<PersonVisit> getPersonVisitFromAQL(String aql) {
		List<Record4<String, String, Instant, Instant>> records = executeAqlQuery(aql);
		if(records.size()==0) {
			throw new UnprocessableEntityException("Visit AQL returned no visits, please review the AQL query");
		}

		Map<RegistryKey, List<Record>> grouped = records.stream()
				.collect(Collectors.groupingBy(r -> new RegistryKey((String)r.value(0), (String)r.value(1))));

		List<PersonVisit> resultPersonVisit = new ArrayList<>();
		groupPersonVisits(grouped, resultPersonVisit);
		return resultPersonVisit;
	}

	private void keepEHRs(List<PersonVisit> resultPersonVisit, Ehrs ehrList) {
		Set<String> ehrsToKeep = new HashSet<>(Arrays.asList(ehrList.getEhrIds()));
		resultPersonVisit.removeIf(visit -> !ehrsToKeep.contains(visit.getEhrId()));
	}


	private void groupPersonVisits(Map<RegistryKey, List<Record>> grouped, List<PersonVisit> resultPersonVisit) {
		for (Entry<RegistryKey, List<Record>> entry : grouped.entrySet()) {
			RegistryKey key = entry.getKey();
			List<Record> group = entry.getValue();



			Date[] dateRange = calculateDateRange(group); //0 minStart, 1 maxEnd
			Date minStartDate = dateRange[0];
			Date maxEndDate = dateRange[1];
			
			Optional<EHRToPerson> ehrtoperson = ehrToPersonService.findByEhrId(key.getEhrId());
			if(ehrtoperson.isPresent())
				resultPersonVisit.add(createVisit(key.getEhrId(), key.getSourceId(), minStartDate, maxEndDate,ehrtoperson.get()));
		}
	}

	/**
	 * This method calculates a single minStart, maxEnd date range from a set of records from the AQL query
	 * @param records
	 * @return A Date[] containing minStart on position 0 and maxEnd on position 1
	 */
	private Date[] calculateDateRange(List<Record> records) {
	    Date minStart = null;
	    Date maxEnd = null;

	    for (Record r : records) {
	        Date start = Date.from((Instant) r.value(2));
	        Date end = r.value(3) != null ? Date.from((Instant) r.value(3)) : start;

	        if (start != null && (minStart == null || start.before(minStart))) {
	            minStart = start;
	        }
	        if (end != null && (maxEnd == null || end.after(maxEnd))) {
	            maxEnd = end;
	        }
	    }

	    return new Date[] { minStart, maxEnd };
	}

	private void saveAll(List<PersonVisit> resultPersonVisit) {
		for(PersonVisit personVisit:resultPersonVisit) {
			Optional<PersonVisit> existente = personVisitRepository.findByEhrIdAndSourceVisit(personVisit.getEhrId(),personVisit.getSourceVisit());
			if (!existente.isPresent()) {
				personVisitRepository.save(personVisit);
			}
			else {
				LOG.info("PersonVisit already exists: {}", personVisit);
			}
		}
	}


	private List<Record4<String, String, Instant, Instant>> executeAqlQuery(String aql) {
		try {
			String querytoRun;
			if(aql!=null) {
				querytoRun=aql;
			}
			else if(visitConverterProperties.getAql()!=null) {
				querytoRun=visitConverterProperties.getAql();
			}
			else {
				return new ArrayList<>();
			}
			
			List<Record4<String, String, Instant, Instant>> execution = runQuery(querytoRun);
			return execution;
		} catch (Exception exception) {
			return new ArrayList<>();
		}
	}


	private List<Record4<String, String, Instant, Instant>> runQuery(String querytoRun) {
		NativeQuery<Record4<String, String, Instant, Instant>> buildNativeQuery;
		List<Record4<String, String, Instant, Instant>> execute;
		buildNativeQuery = Query.buildNativeQuery(querytoRun, String.class,String.class,Instant.class,Instant.class);
		try {
			execute = openEhrClient.aqlEndpoint().execute(buildNativeQuery);
		} catch (IndexOutOfBoundsException e) {
			LOG.error("Visit AQL has less than 4 needed parameters, namely ehr_id, source_id, begin, end");
			throw new UnprocessableEntityException("Visit AQL has less than 4 needed parameters, namely ehr_id, source_id, begin, end");
		}
		catch (Exception e) {
			LOG.error("There was a problem processing visit AQL. AQL needs to return 4 parameters, namely ehr_id, source_id, begin, end");
			throw new UnprocessableEntityException("There was a problem processing visit AQL. AQL needs to return 4 parameters, namely ehr_id, source_id, begin, end");
		}
		return execute;
	}

	private PersonVisit createVisit(String ehrId, String sourceId, Date minStartDate, Date maxEndDate, EHRToPerson ehrToPerson) {

		PersonVisit personVisit = new PersonVisit();
		personVisit.setEhrId(ehrId);
		personVisit.setSourceVisit(sourceId);
		personVisit.setVisitStartDate(minStartDate);
		personVisit.setVisitStartDateTime(minStartDate);
		personVisit.setVisitEndDate(maxEndDate);
		personVisit.setVisitEndDateTime(maxEndDate);
		VisitOccurrence visitOccurrence=new VisitOccurrence();
		visitOccurrence.setVisitTypeConcept(conceptService.findById(32817L));
		visitOccurrence.setVisitConcept(conceptService.findById(9201L));
		visitOccurrence.setVisitStartDate(minStartDate);
		visitOccurrence.setVisitStartDateTime(minStartDate);
		visitOccurrence.setVisitEndDate(maxEndDate);
		visitOccurrence.setVisitEndDateTime(maxEndDate);
		visitOccurrence.setPerson(ehrToPerson.getPerson());
		personVisit.setVisitOccurrence(visitOccurrence);
		return personVisit;
	}

}
