package adeshinaogunmodede.textbin.api.controller;

import adeshinaogunmodede.textbin.api.dto.ErrorDto;
import adeshinaogunmodede.textbin.exception.TextContentCreationException;
import adeshinaogunmodede.textbin.exception.TextContentRetrievalException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ApiErrorHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(ApiErrorHandler.class);

    @ExceptionHandler(TextContentRetrievalException.class)
    public ResponseEntity<ErrorDto> handleInvalidReferenceError(TextContentRetrievalException exception) {
        ErrorDto errorDto = new ErrorDto();
        errorDto.setCode(exception.getCode());
        errorDto.setDescription(exception.getMessage());
        return new ResponseEntity<>(errorDto, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(TextContentCreationException.class)
    public ResponseEntity<ErrorDto> handleTextCreationErrors(TextContentCreationException exception) {
        ErrorDto errorDto = new ErrorDto();
        errorDto.setCode(exception.getCode());
        errorDto.setDescription(exception.getMessage());
        return new ResponseEntity<>(errorDto, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorDto> requestBody(MethodArgumentNotValidException exception) {
        ErrorDto errorDto = new ErrorDto();
        errorDto.setCode("INVALID_REQUEST_BODY_FORMAT");
        errorDto.setDescription("Invalid request body format");
        return new ResponseEntity<>(errorDto, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDto> fallbackHandler(Exception exception) {
        ErrorDto errorDto = new ErrorDto();
        errorDto.setCode("INTERNAL_SERVER_ERROR");
        errorDto.setDescription("Something went wrong on our end. Try Again");
        LOGGER.error("Handling Exception", exception);
        return new ResponseEntity<>(errorDto, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
