package org.bih.eos.services.dao;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.bih.eos.exceptions.UnprocessableEntityException;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ConversionResponseVisit {

	    HashSet<String> visitIds = new HashSet<>();

	    VisitResponse visitResponse = new VisitResponse();
	    static class VisitResponse {

	        @JsonProperty("amount of visits mapped")
	        long visitCount;

	       
	        public void setEhrCount(long value) {
	            visitCount = value;
	        }
	     
	    }

	    public String getJson() {
	        visitResponse.setEhrCount(visitIds.size());
	        ObjectMapper mapper = new ObjectMapper();
	        try {
	            return mapper.writeValueAsString(visitResponse);
	        } catch (JsonProcessingException e) {
	            throw new UnprocessableEntityException("Error converting to json");
	        }
	    }

	    public void increaseOneComposition(String visitid) {
	       
	    	visitIds.add(visitid);

	    }

	}
