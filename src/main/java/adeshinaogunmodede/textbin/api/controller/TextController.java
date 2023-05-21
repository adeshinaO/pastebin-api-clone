package adeshinaogunmodede.textbin.api.controller;

import adeshinaogunmodede.textbin.api.dto.CreateTextDto;
import adeshinaogunmodede.textbin.api.dto.TextDto;
import adeshinaogunmodede.textbin.service.TextService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TextController {

    @Autowired
    private TextService textService;

    @PostMapping("/")
    public ResponseEntity<TextDto> createText(@RequestBody @Validated CreateTextDto createTextDto) {
        TextDto textDto = textService.createText(createTextDto);
        return new ResponseEntity<>(textDto, HttpStatus.CREATED);
    }

    @GetMapping("/{reference}")
    public ResponseEntity<TextDto> getText(@PathVariable("reference") String reference) {
        TextDto textDto = textService.getText(reference);
        return new ResponseEntity<>(textDto, HttpStatus.OK);
    }
}
