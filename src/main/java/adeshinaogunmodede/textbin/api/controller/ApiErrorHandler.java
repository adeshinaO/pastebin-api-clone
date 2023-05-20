package adeshinaogunmodede.textbin.api.controller;

import adeshinaogunmodede.textbin.api.dto.ErrorDto;
import adeshinaogunmodede.textbin.exception.InvalidTextReferenceException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ApiErrorHandler {

    @ExceptionHandler(InvalidTextReferenceException.class)
    public ResponseEntity<ErrorDto> handleInvalidReferenceError(InvalidTextReferenceException exception) {
        // todo: get code from exception
        return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDto> fallbackHandler(Exception exception) {
        // todo:
        return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
