package adeshinaogunmodede.textbin.service;

import adeshinaogunmodede.textbin.api.dto.CreateTextDto;
import adeshinaogunmodede.textbin.api.dto.TextDto;

public interface TextService {

    TextDto createText(CreateTextDto createTextDto);
    TextDto getText(String reference);
}
