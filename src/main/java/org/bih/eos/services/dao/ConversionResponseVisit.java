package org.bih.eos.services.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.bih.eos.exceptions.UnprocessableEntityException;
import org.bih.eos.jpabase.entity.PersonVisit;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 
 * This class is used for Json generation of the Visit web service response. Json contains a set of visit summaries, one for each generated visit,
 * and a visit response for a count of generated visits
 * @author Yampeku
 *
 */
public class ConversionResponseVisit {

	private final Set<String> visitIds = new HashSet<>();
	private final List<VisitSummary> visits = new ArrayList<>();

	public static class VisitSummary {
		@JsonProperty("ehr_id")
		private String ehrId;

		@JsonProperty("source_visit")
		private String sourceVisit;

		@JsonProperty("visit_start_date")
		@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
		private Date visitStartDate;

		@JsonProperty("visit_end_date")
		@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
		private Date visitEndDate;

		public VisitSummary(PersonVisit pv) {
			this.ehrId = pv.getEhrId();
			this.sourceVisit = pv.getSourceVisit();
			this.visitStartDate = pv.getVisitStartDate();
			this.visitEndDate = pv.getVisitEndDate();
		}

		public String getEhrId() { return ehrId; }
		public String getSourceVisit() { return sourceVisit; }
		public Date getVisitStartDate() { return visitStartDate; }
		public Date getVisitEndDate() { return visitEndDate; }
	}

	static class VisitResponse {

		@JsonProperty("amount of visits mapped")
		private long visitCount;

		@JsonProperty("visits")
		private List<VisitSummary> visits;

		public VisitResponse(long visitCount, List<VisitSummary> visits) {
			this.visitCount = visitCount;
			this.visits = visits;
		}

		public long getVisitCount() { return visitCount; }
		public List<VisitSummary> getVisits() { return visits; }
	}

	public String getJson() {
		VisitResponse response = new VisitResponse(visitIds.size(), visits);
		ObjectMapper mapper = new ObjectMapper();
		try {
			return mapper.writeValueAsString(response);
		} catch (JsonProcessingException e) {
			throw new UnprocessableEntityException("Error converting to json");
		}
	}

	public void addPersonVisit(PersonVisit pv) {
		if (visitIds.add(pv.getSourceVisit())) {
			visits.add(new VisitSummary(pv));
		}
	}

}
