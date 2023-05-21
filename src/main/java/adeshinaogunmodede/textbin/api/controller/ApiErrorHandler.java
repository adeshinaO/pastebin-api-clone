package adeshinaogunmodede.textbin.api.controller;

import adeshinaogunmodede.textbin.api.dto.ErrorDto;
import adeshinaogunmodede.textbin.exception.ErrorCode;
import adeshinaogunmodede.textbin.exception.TextContentRetrievalException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ApiErrorHandler {

    @ExceptionHandler(TextContentRetrievalException.class)
    public ResponseEntity<ErrorDto> handleInvalidReferenceError(TextContentRetrievalException exception) {

        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDto> fallbackHandler(Exception exception) {
        // todo:
        return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
