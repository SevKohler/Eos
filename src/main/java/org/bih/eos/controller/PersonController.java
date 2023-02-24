package org.bih.eos.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.bih.eos.controller.dao.Ehrs;
import org.bih.eos.services.PersonEndpointService;
import org.ehrbase.client.aql.query.Query;
import org.ehrbase.client.aql.record.Record1;
import org.ehrbase.client.openehrclient.OpenEhrClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(path = "/person")
public class PersonController {
    private final PersonEndpointService personEndpointService;
    private final OpenEhrClient openEhrClient;


    public PersonController(PersonEndpointService personEndpointService, OpenEhrClient openEhrClient){
        this.personEndpointService = personEndpointService;
        this.openEhrClient = openEhrClient;
    }


    @Transactional
    @PostMapping(
            produces = "application/json"
    )
    public ResponseEntity<Object> addEHRs() {
        return convertAll();
    }

    @Transactional
    @PostMapping(
            consumes = "application/json",
            produces = "application/json")
    public ResponseEntity<Object> addEHRs(@RequestBody String json) {
        return convertList(json);
    }

    private ResponseEntity<Object> convertList( String json) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            Ehrs ehrs = objectMapper.readValue(json, Ehrs.class);
            return new ResponseEntity<>(personEndpointService.createEhr(ehrs), HttpStatus.OK);
        } catch (JsonProcessingException e) {
            return new ResponseEntity<>("{ \"message\" : \"Json malformed\" }", HttpStatus.BAD_REQUEST);
        }
    }

    private ResponseEntity convertAll() {
        List<Record1<UUID>> result = executeAqlQuery();
        List<UUID> ehrs = new ArrayList<>();
        result.forEach(r -> ehrs.add(r.value1()));
        return new ResponseEntity<>(personEndpointService.createEhr(ehrs), HttpStatus.OK);
    }

    private List<Record1<UUID>> executeAqlQuery() {
        Query<Record1<UUID>> query = Query.buildNativeQuery("SELECT e/ehr_id/value FROM EHR e", UUID.class);
        try {
            return openEhrClient.aqlEndpoint().execute(query);
        } catch (NullPointerException nullPointerException) {
            return new ArrayList<>();
        }
    }
}


