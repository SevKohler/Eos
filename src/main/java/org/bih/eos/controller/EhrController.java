package org.bih.eos.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.bih.eos.controller.dao.Ehrs;
import org.bih.eos.jpabase.dba.service.EHRToPersonService;
import org.bih.eos.services.ConverterService;
import org.bih.eos.services.EhrService;
import org.ehrbase.client.openehrclient.OpenEhrClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/ehr")
public class EhrController {
    private final ConverterService converterService;
    private final EHRToPersonService ehrToPersonService;
    private final OpenEhrClient openEhrClient;

    public EhrController(ConverterService conversionService, EHRToPersonService ehrToPersonService, OpenEhrClient openEhrClient) {
        this.converterService = conversionService;
        this.ehrToPersonService = ehrToPersonService;
        this.openEhrClient = openEhrClient;
    }

    @PostMapping(
            produces = "application/json")
    public ResponseEntity<Object> convertAll() {
        return new ResponseEntity<>(new EhrService(ehrToPersonService, openEhrClient, converterService).convertAll(), HttpStatus.OK);
    }

    @PostMapping(
            consumes = "application/json",
            produces = "application/json")
    public ResponseEntity<Object> convertAll(@RequestBody String json) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            Ehrs ehrs = objectMapper.readValue(json, Ehrs.class);
            return new ResponseEntity<>(new EhrService(ehrToPersonService, openEhrClient, converterService).convertEhrList(ehrs), HttpStatus.OK);
        } catch (JsonProcessingException e) {
            return new ResponseEntity<>("{ \"message\" : \"Json malformed\" }", HttpStatus.BAD_REQUEST);
        }
    }
}
