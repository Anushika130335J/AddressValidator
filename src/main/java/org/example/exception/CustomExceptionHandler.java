package org.example.exception;

import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;

@RestControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(HttpClientErrorException.class)
    public String addressvalidatorException(HttpClientErrorException ex) {
        return ex.getMessage();
    }

}
