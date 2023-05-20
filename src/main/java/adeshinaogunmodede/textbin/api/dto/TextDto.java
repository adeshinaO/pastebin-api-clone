package adeshinaogunmodede.textbin.api.dto;

import lombok.Data;

@Data
public class TextDto {
    private String content;
    private String expiryDate;
    private boolean hasExpiryDate;
}
