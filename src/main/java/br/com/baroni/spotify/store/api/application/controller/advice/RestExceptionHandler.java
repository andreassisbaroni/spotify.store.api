package br.com.baroni.spotify.store.api.application.controller.advice;

import br.com.baroni.spotify.store.api.application.controller.advice.details.ExceptionDetails;
import br.com.baroni.spotify.store.api.domain.exception.GenericException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(GenericException.class)
    public ResponseEntity<ExceptionDetails> handleResourceNotFoundException(GenericException genericException) {
        ExceptionDetails details = new ExceptionDetails(genericException.getMessage(), HttpStatus.BAD_REQUEST);

        return new ResponseEntity<>(details, HttpStatus.BAD_REQUEST);
    }
}
