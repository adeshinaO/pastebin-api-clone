package adeshinaogunmodede.textbin.service;

import adeshinaogunmodede.textbin.api.dto.CreateTextDto;
import adeshinaogunmodede.textbin.api.dto.TextDto;

public interface TextService {

    /**
     *
     * @param createTextDto
     * @return
     */
    TextDto createText(CreateTextDto createTextDto);

    /**
     *
     * @param reference
     * @return
     */
    TextDto getText(String reference);
}
