package adeshinaogunmodede.textbin.exception;

import lombok.Data;

@Data
public class TextContentRetrievalException extends RuntimeException {
    private final String code;
    public TextContentRetrievalException(String message, String code) {
        super(message);
        this.code = code;
    }
}
