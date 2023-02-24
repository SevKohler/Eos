package org.bih.eos.controller;

import com.nedap.archie.rm.composition.Composition;
import org.bih.eos.jpabase.dba.service.EHRToPersonService;
import org.bih.eos.jpabase.model.entity.EHRToPerson;
import org.bih.eos.jpabase.model.entity.JPABaseEntity;
import org.bih.eos.jpabase.model.entity.Person;
import org.bih.eos.services.ConverterService;
import org.bih.eos.services.dao.ConvertableComposition;
import org.ehrbase.serialisation.jsonencoding.CanonicalJson;
import org.ehrbase.serialisation.xmlencoding.CanonicalXML;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "ehr/{ehrId}/composition")
public class CompositionController {

    private final ConverterService conversionService;
    private final EHRToPersonService ehrToPersonService;

    public CompositionController(ConverterService conversionService, EHRToPersonService ehrToPersonService){
        this.conversionService = conversionService;
        this.ehrToPersonService = ehrToPersonService;
    }

    @PostMapping(
            consumes = "application/json",
            produces = "application/json")
    public ResponseEntity<Object> addCompositionJson(@RequestBody String json, @PathVariable String ehrId) {
        Optional<EHRToPerson> ehrToPerson = ehrToPersonService.findByEhrId(ehrId);
        if (ehrToPerson.isPresent()) {
            Composition composition = new CanonicalJson().
                    unmarshal(json, com.nedap.archie.rm.composition.Composition.class);
            System.out.println(composition);
            return runConversion(composition, ehrToPerson.get().getPerson());
        } else {
            return new ResponseEntity<>("{ \"message\" : \"EHR id has no person, create either one or enter a correct ehr id\" }", HttpStatus.CONFLICT);
        }
    }

    @PostMapping(
            consumes = "application/xml",
            produces = "application/xml")
    public ResponseEntity<Object> addCompositionXML(@RequestBody String xml, @PathVariable String ehrId) {
        Optional<EHRToPerson> ehrToPerson = ehrToPersonService.findByEhrId(ehrId);
        if (ehrToPerson.isPresent()) {
            Composition composition = new CanonicalXML().
                    unmarshal(xml, com.nedap.archie.rm.composition.Composition.class);
            return runConversion(composition, ehrToPerson.get().getPerson());
        } else {
            return new ResponseEntity<>("{ \"message\" : \"EHR id has no person, create either one or enter a correct ehr id\" }", HttpStatus.CONFLICT);
        }
    }

    private ResponseEntity<Object> runConversion(Composition composition, Person person) {
        List<JPABaseEntity> results = conversionService.convert(new ConvertableComposition(composition, person));
        if(results.size()==0){
            return new ResponseEntity<>("{ \"message\" : \"Nothing was converted, either a required field was missing, or no converter for the archetype configured. For more information inspect the logs.\" }", HttpStatus.OK);
        }else{
            return new ResponseEntity<>(results, HttpStatus.OK);
        }
    }


}
