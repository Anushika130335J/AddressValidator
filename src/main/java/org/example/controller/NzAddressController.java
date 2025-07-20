package org.example.controller;

import org.example.beans.*;
import org.example.service.ExternalApiService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NzAddressController {

    Logger log = LoggerFactory.getLogger(NzAddressController.class);
    ExternalApiService nzAddressService;

    public NzAddressController(ExternalApiService nzAddressService) {
        this.nzAddressService = nzAddressService;
    }

    @PostMapping("/verification")
    public ResponseEntity validateAddress(@RequestBody ValidateAddressBeanRq validateAddressBeanRq) {

        ValidateAddressBeanRs isValid = nzAddressService.getValidation(validateAddressBeanRq);
        return ResponseEntity.ok(isValid);
    }

    @GetMapping("/ping")
    public ResponseEntity<ResponseDto> ping() {
        return ResponseEntity.ok(new ResponseDto("Hello, World!"));
    }

    @PostMapping("/autocomplete")
    public ResponseEntity validateAddress(@RequestBody AutoCompleteRq autoCompleteRq) {
        AutoCompleteRs autoCompleted = nzAddressService.getAutoComplete(autoCompleteRq);
        return ResponseEntity.ok(autoCompleted);
    }


}
