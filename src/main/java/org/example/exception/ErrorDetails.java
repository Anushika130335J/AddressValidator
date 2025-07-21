package org.example.exception;

import lombok.Data;

@Data
public class ErrorDetails {

    private int status;
    private String message;
    private long timestamp;

    public ErrorDetails(int status, String message, long timestamp) {
        this.status = status;
        this.message = message;
        this.timestamp = timestamp;
    }
}
