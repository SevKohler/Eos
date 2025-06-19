package org.bih.eos.controller;

import org.bih.eos.controller.dao.Ehrs;
import org.bih.eos.services.VisitEndpointService;
import org.bih.eos.services.VisitService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping(path = "/visit")
public class VisitController {
    private final VisitEndpointService visitEndpointService;

    public VisitController(VisitEndpointService visitEndpointService) {
        this.visitEndpointService = visitEndpointService;
    }

    @PostMapping(
            produces = "application/json")
    public ResponseEntity<Object> convertAll(
            @RequestParam(required = false) String aql,
            @RequestParam(required = false) String templateid,
            @RequestParam(required = false) String visitsource
    		) {
        return new ResponseEntity<>(new VisitService(visitEndpointService, aql).generateAllVisits(), HttpStatus.OK);
    }

    @PostMapping(
            consumes = "application/json",
            produces = "application/json")
    public ResponseEntity<Object> convertAll(@RequestBody String json) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            Ehrs ehrs = objectMapper.readValue(json, Ehrs.class);
            return new ResponseEntity<>(new VisitService(visitEndpointService).generateSpecificVisits(ehrs), HttpStatus.OK);
        } catch (JsonProcessingException e) {
            return new ResponseEntity<>("{ \"message\" : \"Json malformed\" }", HttpStatus.BAD_REQUEST);
        }
    }
}
