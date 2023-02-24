package org.bih.eos.controller;


import org.bih.eos.services.ErasPeriodService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(path = "/eras_period")
public class ErasPeriodController {
    ErasPeriodService erasPeriodService;

    public ErasPeriodController(ErasPeriodService erasPeriodService) {
        this.erasPeriodService = erasPeriodService;
    }

    @Transactional
    @PostMapping(
            produces = "application/json"
    )
    public ResponseEntity<Object> runErasPeriod() {
        erasPeriodService.executeSqls();
        return new ResponseEntity<>(HttpStatus.OK);
    }
}


