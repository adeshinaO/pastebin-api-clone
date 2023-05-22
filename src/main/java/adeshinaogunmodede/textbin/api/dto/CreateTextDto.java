package adeshinaogunmodede.textbin.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateTextDto {

    @NotBlank(message = "Content cannot be empty")
    private String content;

    @NotNull(message = "Cannot be null")
    private boolean hasExpiryDate;

    private String expiryDate;
}
