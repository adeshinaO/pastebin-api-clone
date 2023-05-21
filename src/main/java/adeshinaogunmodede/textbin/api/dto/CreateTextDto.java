package adeshinaogunmodede.textbin.api.dto;

import adeshinaogunmodede.textbin.util.RegexPatterns;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class CreateTextDto {

    @NotBlank(message = "TODO>>>>>>>>")
    private String content; // todo: use a config property in service class to check max allowed size

    @NotNull(message = "MESSSS>>>> TODO")
    private boolean hasExpiryDate;

    @Pattern(regexp = RegexPatterns.VALID_DATE_TIME_AND_ZONE, message = "TODO>>>>>>>>>")
    private String expiryDate;

    // todo: use a text pattern and a regex. better control of message.
}
