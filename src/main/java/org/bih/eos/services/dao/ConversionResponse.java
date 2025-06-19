package org.bih.eos.services.dao;

import java.util.ArrayList;
import java.util.List;

import org.bih.eos.exceptions.UnprocessableEntityException;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ConversionResponse {

    List<String> ehrIds = new ArrayList<>();

    EhrResponse ehrResponse = new EhrResponse();
    static class EhrResponse {

        @JsonProperty("amount of ehrs mapped")
        long ehrCount;
        @JsonProperty("amount of clinical data table rows added")
        long convertedOmopEntities;
        @JsonProperty("amount of compositions mapped")
        long convertedCompositions;

        public void increaseConvertedOmopEntities(long value) {
            convertedOmopEntities += value;
        }

        public void setEhrCount(long value) {
            ehrCount = value;
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
        ehrResponse.setEhrCount(ehrIds.size());
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(ehrResponse);
        } catch (JsonProcessingException e) {
            throw new UnprocessableEntityException("Error converting to json");
        }
    }

    public void increaseOneComposition(String ehrId, int size) {
        if (ehrIds.contains(ehrId)) {
            ehrResponse.increaseConvertedOmopEntities(size);
            ehrResponse.increaseConvertedCompositions(1);
        }else{
            ehrIds.add(ehrId);
            ehrResponse.increaseConvertedOmopEntities(size);
            ehrResponse.increaseConvertedCompositions(1);
        }
    }
}
