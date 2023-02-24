package org.bih.eos.services.dao;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.bih.eos.exceptions.UnprocessableEntityException;

import java.util.HashMap;

public class ConversionResponse {
    HashMap<String, EhrResponse> ehrResponses = new HashMap<>();

    static class EhrResponse {
        @JsonProperty("amount of clinical data table rows added")
        long convertedOmopEntities;
        @JsonProperty("amount of compositions mapped")
        long convertedCompositions;

        public EhrResponse(long convertedOmopEntities, long convertedCompositions) {
            this.convertedOmopEntities = convertedOmopEntities;
            this.convertedCompositions = convertedCompositions;
        }

        public void increaseConvertedOmopEntities(long value) {
            convertedOmopEntities += value;
        }

        public void increaseConvertedCompositions(long value) {
            convertedCompositions += value;
        }

        public long getConvertedOmopEntities() {
            return convertedOmopEntities;
        }

        public long getConvertedCompositions() {
            return convertedCompositions;
        }
    }

    public String getJson() {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(ehrResponses);
        } catch (JsonProcessingException e) {
            throw new UnprocessableEntityException("Error converting to json");
        }
    }

    public void increaseOneComposition(String ehrId, int size) {
        if(ehrResponses.containsKey(ehrId)){
            EhrResponse ehrResponse = ehrResponses.get(ehrId);
            ehrResponse.increaseConvertedOmopEntities(size);
            ehrResponse.increaseConvertedCompositions(1);
        }else{
            ehrResponses.put(ehrId, new EhrResponse(1, size));
        }
    }
}
