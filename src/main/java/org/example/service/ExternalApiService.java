package org.example.service;

import org.example.beans.AutoCompleteRq;
import org.example.beans.AutoCompleteRs;
import org.example.beans.ValidateAddressBeanRq;
import org.example.beans.ValidateAddressBeanRs;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.logging.Logger;

@Service
public class ExternalApiService {

    Logger log = Logger.getLogger(ExternalApiService.class.getName());
    private RestTemplate restTemplate;
    private final String VALIDATION_URL = "https://api.addressfinder.io/api/nz/address/verification";
    private final String AUTOCOMPLETE_URL = "https://api.addressfinder.io/api/nz/address/autocomplete";

    public ExternalApiService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public ValidateAddressBeanRs getValidation(ValidateAddressBeanRq validateAddressBeanRq) {
        log.info("Calling external API");
        HttpEntity<ValidateAddressBeanRq> address = new HttpEntity<>(validateAddressBeanRq);
        ResponseEntity<ValidateAddressBeanRs> response = restTemplate.postForEntity(VALIDATION_URL, address, ValidateAddressBeanRs.class);
        return response.getBody();
    }

    public AutoCompleteRs getAutoComplete(AutoCompleteRq autoCompleteRq) {
        log.info("Calling external API");
        HttpEntity<AutoCompleteRq> address = new HttpEntity<>(autoCompleteRq);
        ResponseEntity<AutoCompleteRs> response = restTemplate.postForEntity(AUTOCOMPLETE_URL, address, AutoCompleteRs.class);
        return response.getBody();
    }
}
