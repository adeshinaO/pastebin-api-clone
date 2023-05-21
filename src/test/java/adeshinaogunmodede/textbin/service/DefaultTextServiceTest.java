package adeshinaogunmodede.textbin.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import adeshinaogunmodede.textbin.api.dto.CreateTextDto;
import adeshinaogunmodede.textbin.api.dto.TextDto;
import adeshinaogunmodede.textbin.exception.ErrorCode;
import adeshinaogunmodede.textbin.exception.TextContentCreationException;
import adeshinaogunmodede.textbin.exception.TextContentRetrievalException;
import adeshinaogunmodede.textbin.model.Text;
import adeshinaogunmodede.textbin.repository.TextRepository;
import adeshinaogunmodede.textbin.service.impl.DefaultTextService;
import java.time.ZonedDateTime;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class DefaultTextServiceTest {

    @Mock
    private TextRepository textRepository;

    @Test
    @DisplayName("Create Text Record From Request DTO")
    public void shouldCreateTextRecord() {

        String content = "I have a big blue Toyota Truck. I have two smart watches(not really)";
        String expiryDateStr = "2023-09-03T02:01:00+01:00";

        CreateTextDto createTextDto = new CreateTextDto();
        createTextDto.setContent(content);
        createTextDto.setExpiryDate(expiryDateStr);
        createTextDto.setHasExpiryDate(true);

        Text text = new Text();

        when(textRepository.findByReference(anyString())).thenReturn(Optional.empty());
        when(textRepository.save(any(Text.class))).thenReturn(text);

        DefaultTextService textService = new DefaultTextService(textRepository, 10, 175);

        ArgumentCaptor<Text> textArgumentCaptor = ArgumentCaptor.forClass(Text.class);

        textService.createText(createTextDto);

        verify(textRepository).findByReference(anyString());
        verify(textRepository).save(textArgumentCaptor.capture());

        text = textArgumentCaptor.getValue();

        Assertions.assertEquals(ZonedDateTime.parse(expiryDateStr), text.getExpiryDate());
        Assertions.assertTrue(text.isHasExpiryDate());
        Assertions.assertEquals(content, text.getContent());
    }

    @Test
    @DisplayName("Should Not Create Text Record When Expiry Date Is In The Past")
    public void shouldNotCreateTextRecordForInvalidExpiryDate() {

        String content = "I have a big blue Toyota Truck. I have two big cars!(No, I don't)";
        String expiryDateStr = "2023-01-03T02:01:00+01:00";

        CreateTextDto createTextDto = new CreateTextDto();
        createTextDto.setContent(content);
        createTextDto.setExpiryDate(expiryDateStr);
        createTextDto.setHasExpiryDate(true);

        DefaultTextService textService = new DefaultTextService(textRepository, 10, 175);

        String code = Assertions.assertThrows(TextContentCreationException.class,
                () -> textService.createText(createTextDto)).getCode();

        Assertions.assertEquals(ErrorCode.INVALID_EXPIRY_DATE, code);
    }

    @Test
    @DisplayName("Should Not Create Text Record When Content Length Is Unacceptable")
    public void shouldNotCreateTextRecordForInvalidContentLength() {

        String content = "YES!"; // Not Long Enough
        String expiryDateStr = "2023-01-03T02:01:00+01:00";

        int minContentLength = 5;
        int maxContentLength = 7;

        CreateTextDto createTextDto = new CreateTextDto();
        createTextDto.setContent(content);
        createTextDto.setExpiryDate(expiryDateStr);
        createTextDto.setHasExpiryDate(true);

        DefaultTextService textService = new DefaultTextService(textRepository, minContentLength, maxContentLength);

        String code = Assertions.assertThrows(TextContentCreationException.class,
                () -> textService.createText(createTextDto)).getCode();

        Assertions.assertEquals(ErrorCode.UNACCEPTABLE_CONTENT_LENGTH, code);

        createTextDto.setContent("Much longer content - Should fail too");

        code = Assertions.assertThrows(TextContentCreationException.class, () -> textService.createText(createTextDto)).getCode();

        Assertions.assertEquals(ErrorCode.UNACCEPTABLE_CONTENT_LENGTH, code);
    }


    @Test
    @DisplayName("Should Return DTO For Text With Given Reference")
    public void returnDtoForTextWithGivenReference() {

        String reference = "ref_2";
        String content = "The content";
        String expiryDateStr = "2023-01-03T02:01:22+01:00";

        Text text = new Text();
        text.setReference(reference);
        text.setContent(content);
        text.setHasExpiryDate(true);
        text.setExpiryDate(ZonedDateTime.parse(expiryDateStr));

        when(textRepository.findByReference(reference)).thenReturn(Optional.of(text));
        DefaultTextService textService = new DefaultTextService(textRepository, 1, 1);

        TextDto textDto = textService.getText(reference);

        verify(textRepository).findByReference(reference);

        Assertions.assertEquals(expiryDateStr, textDto.getExpiryDate());
        Assertions.assertEquals(content, textDto.getContent());
    }

    @Test
    @DisplayName("Throw Exception When Text With Given Reference Has Expired")
    public void throwExceptionWhenReferenceMatchesExpiredText() {

        String reference = "ref_2";
        String content = "The content";
        String expiryDateStr = "2023-01-03T02:01:22+01:00"; // Date in past

        Text text = new Text();
        text.setReference(reference);
        text.setContent(content);
        text.setHasExpiryDate(true);
        text.setExpiryDate(ZonedDateTime.parse(expiryDateStr));

        when(textRepository.findByReference(reference)).thenReturn(Optional.of(text));
        DefaultTextService textService = new DefaultTextService(textRepository, 1, 1);

        String code = Assertions.assertThrows(TextContentRetrievalException.class, () -> textService.getText(reference)).getCode();

        verify(textRepository).findByReference(reference);
        Assertions.assertEquals(ErrorCode.REFERENCE_TO_EXPIRED_TEXT, code);
    }

    @Test
    @DisplayName("Throw Exception When Reference Doesn't Match A Database Record")
    public void throwExceptionWhenReferenceIsInvalid() {

        String reference = "ref_2";

        when(textRepository.findByReference(reference)).thenReturn(Optional.empty());
        DefaultTextService textService = new DefaultTextService(textRepository, 1, 1);

        String code = Assertions.assertThrows(TextContentRetrievalException.class, () -> textService.getText(reference)).getCode();

        verify(textRepository).findByReference(reference);
        Assertions.assertEquals(ErrorCode.INVALID_TEXT_REFERENCE, code);
    }
}
