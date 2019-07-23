package br.com.baroni.spotify.store.api.application.controller.advice.details;

import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

public class ExceptionDetails {

    private LocalDateTime timestamp;
    private String message;
    private HttpStatus status;

    private ExceptionDetails() {
        super();
        this.setTimestamp(LocalDateTime.now());
    }

    public ExceptionDetails(String message, HttpStatus status) {
        this();
        this.setMessage(message);
        this.setStatus(status);
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }
}
