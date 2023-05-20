package adeshinaogunmodede.textbin.exception;

import lombok.Data;

@Data
public class InvalidTextReferenceException extends RuntimeException {

    private final String code = "INVALID_TEXT_REFERENCE";

    public InvalidTextReferenceException(String message) {
        super(message);
    }
}
