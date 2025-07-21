package org.example.controller;

import org.example.beans.*;
import org.example.service.ExternalApiService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(
        origins = "http://addressvalidator1.s3-website-ap-southeast-2.amazonaws.com/",
        methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.OPTIONS},
        allowedHeaders = {"Origin", "Content-Type", "Accept", "Authorization"},
        maxAge = 3600
)
public class NzAddressController {

    Logger log = LoggerFactory.getLogger(NzAddressController.class);
    ExternalApiService nzAddressService;

    public NzAddressController(ExternalApiService nzAddressService) {
        this.nzAddressService = nzAddressService;
    }

    @PostMapping("/verification")
    public ResponseEntity validateAddress(@Validated @RequestBody QueryObject queryObject) {
        log.info("Received request for validation");
        log.debug("Query object for validation: {}", queryObject);
        ValidateAddressBeanRs isValid = nzAddressService.getValidation(queryObject);
        log.debug("Validation result: {}", isValid);
        return ResponseEntity.ok(isValid);
    }

    @GetMapping("/ping")
    public ResponseEntity<ResponseDto> ping() {
        return ResponseEntity.ok(new ResponseDto("Hello, World!"));
    }

    @PostMapping("/autocomplete")
    public ResponseEntity autoComplete(@RequestBody QueryObject queryObject) {
        log.info("Received request for autocomplete");
        log.debug("Query object for autocomplete: {}", queryObject);
        AutoCompleteRs autoCompleted = nzAddressService.getAutoComplete(queryObject);
        log.debug("Auto complete result: {}", autoCompleted);
        return ResponseEntity.ok(autoCompleted);
    }

}
