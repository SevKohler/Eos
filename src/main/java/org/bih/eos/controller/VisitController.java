package org.bih.eos.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.bih.eos.controller.dao.Ehrs;
import org.bih.eos.jpabase.service.EHRToPersonService;
import org.bih.eos.services.ConverterService;
import org.bih.eos.services.EhrService;
import org.bih.eos.services.VisitEndpointService;
import org.bih.eos.services.VisitService;
import org.ehrbase.client.openehrclient.OpenEhrClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/visit")
public class VisitController {
    private final ConverterService converterService;
    private final EHRToPersonService ehrToPersonService;
    private final VisitEndpointService visitEndpointService;
    private final OpenEhrClient openEhrClient;

    public VisitController(ConverterService conversionService, EHRToPersonService ehrToPersonService, VisitEndpointService visitEndpointService, OpenEhrClient openEhrClient) {
        this.converterService = conversionService;
        this.ehrToPersonService = ehrToPersonService;
        this.visitEndpointService = visitEndpointService;
        this.openEhrClient = openEhrClient;
    }

    @PostMapping(
            produces = "application/json")
    public ResponseEntity<Object> convertAll() {
        return new ResponseEntity<>(new VisitService(ehrToPersonService,visitEndpointService, openEhrClient, converterService).generateAllVisits(), HttpStatus.OK);
    }

    @PostMapping(
            consumes = "application/json",
            produces = "application/json")
    public ResponseEntity<Object> convertAll(@RequestBody String json) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            Ehrs ehrs = objectMapper.readValue(json, Ehrs.class);
            return new ResponseEntity<>(new VisitService(ehrToPersonService,visitEndpointService, openEhrClient, converterService).generateSpecificVisits(ehrs), HttpStatus.OK);
        } catch (JsonProcessingException e) {
            return new ResponseEntity<>("{ \"message\" : \"Json malformed\" }", HttpStatus.BAD_REQUEST);
        }
    }
}
