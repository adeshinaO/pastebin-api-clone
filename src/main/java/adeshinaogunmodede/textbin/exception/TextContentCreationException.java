package adeshinaogunmodede.textbin.exception;

import lombok.Data;

@Data
public class TextContentCreationException extends RuntimeException {
    private final String code;

    public TextContentCreationException(String message, String code) {
        super(message);
        this.code = code;
    }
}
