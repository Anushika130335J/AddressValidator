package org.example.service;

import org.example.beans.*;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class ExternalApiService {

    Logger log = LoggerFactory.getLogger(ExternalApiService.class.getName());
    private RestTemplate restTemplate;

    @Value("${autocomplete.url}")
    private String AUTOCOMPLETE_URL;

    @Value("${validation.url}")
    private String VALIDATION_URL;

    @Value("${api.key}")
    private String API_KEY;

    @Value("${api.secret}")
    private String API_SECRET;

    public ExternalApiService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    //External API call for validate the address
    public ValidateAddressBeanRs getValidation(QueryObject queryObject) {
        log.info("Calling external API for validation");
        log.debug("Query object for validation: {}", queryObject);
        HttpEntity<ValidateAddressBeanRq> address = generateValidationObject(queryObject.getQuery());
        ResponseEntity<ValidateAddressBeanRs> response = restTemplate.postForEntity(VALIDATION_URL, address, ValidateAddressBeanRs.class);
        log.debug("Validation result: {}", response.getBody());
        return response.getBody();
    }

    private HttpEntity<ValidateAddressBeanRq> generateValidationObject(String query) {
        ValidateAddressBeanRq validateAddressBeanRq = new ValidateAddressBeanRq();
        validateAddressBeanRq.setKey(API_KEY);
        validateAddressBeanRq.setSecret(API_SECRET);
        validateAddressBeanRq.setFormat("json");
        validateAddressBeanRq.setQ(query);
        return new HttpEntity<>(validateAddressBeanRq);
    }

    //External API call for autocomplete the address
    public AutoCompleteRs getAutoComplete(QueryObject queryObject) {
        log.info("Calling external API for autocomplete");
        log.debug("Query object for autocomplete: {}", queryObject);
        HttpEntity<AutoCompleteRq> address = generateAutoCompleteObject(queryObject.getQuery());
        ResponseEntity<AutoCompleteRs> response = restTemplate.postForEntity(AUTOCOMPLETE_URL, address, AutoCompleteRs.class);
        log.debug("Auto complete result: {}", response.getBody());
        return response.getBody();
    }

    private HttpEntity<AutoCompleteRq> generateAutoCompleteObject(String query) {
        AutoCompleteRq autoCompleteRq = new AutoCompleteRq();
        autoCompleteRq.setKey(API_KEY);
        autoCompleteRq.setSecret(API_SECRET);
        autoCompleteRq.setFormat("json");
        autoCompleteRq.setQuery(query);
        return new HttpEntity<>(autoCompleteRq);
    }
}
