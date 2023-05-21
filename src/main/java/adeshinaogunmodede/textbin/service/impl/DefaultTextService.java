package adeshinaogunmodede.textbin.service.impl;

import adeshinaogunmodede.textbin.api.dto.CreateTextDto;
import adeshinaogunmodede.textbin.api.dto.TextDto;
import adeshinaogunmodede.textbin.exception.ErrorCode;
import adeshinaogunmodede.textbin.exception.TextContentCreationException;
import adeshinaogunmodede.textbin.exception.TextContentRetrievalException;
import adeshinaogunmodede.textbin.model.Text;
import adeshinaogunmodede.textbin.repository.TextRepository;
import adeshinaogunmodede.textbin.service.TextService;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

@Service
public class DefaultTextService implements TextService {

    private final TextRepository textRepository;
    private final int minimumContentLength;
    private final int maximumContentLength;

    @Autowired
    public DefaultTextService(
            TextRepository textRepository,
            @Value("${text_content.length.minimum:300}") int minimumContentLength,
            @Value("${text_content.length.maximum:25000}") int maximumContentLength) {
        this.textRepository = textRepository;
        this.minimumContentLength = minimumContentLength;
        this.maximumContentLength = maximumContentLength;
    }

    // TODO: (1 pomodoro = 25mins)
    //  1 pmd - Write and test Dockerfile. Should run maven build too.
    //  1 pmd - Write README. (List Flyway as a possible improvement. another is using block storage for text content
    //                        and a dedicated key generation service. Integr. tests improvement too. env properties too)
    //  1 pmd - Manual Tests, Final Commits.

    @Override
    public TextDto createText(CreateTextDto createTextDto) {

        String content = createTextDto.getContent();
        validateContentLength(content.length());

        Text text = new Text();
        text.setContent(content);

        if (createTextDto.isHasExpiryDate()) {
            ZonedDateTime expiryDate = getExpiryDate(createTextDto.getExpiryDate());
            text.setExpiryDate(expiryDate);
            text.setHasExpiryDate(true);
        }

        text.setReference(generateReference(content.substring(0, minimumContentLength)));
        text = textRepository.save(text);

        return mapTextDto(text);
    }

    @Override
    public TextDto getText(String reference) {
        Text text = textRepository.findByReference(reference).orElseThrow(() ->
                new TextContentRetrievalException("No record matches given reference", ErrorCode.INVALID_TEXT_REFERENCE));
        checkForTextExpiration(text);
        return mapTextDto(text);
    }

    private void checkForTextExpiration(Text text) {
        if (text.isHasExpiryDate()) {
            if (text.getExpiryDate().isBefore(ZonedDateTime.now())) {
                throw new TextContentRetrievalException("Expired text", ErrorCode.REFERENCE_TO_EXPIRED_TEXT);
            }
        }
    }

    private void validateContentLength(int contentLength) {
        if (contentLength < minimumContentLength || contentLength > maximumContentLength) {
            String errMsg = String.format("Acceptable number of characters for text content is %s - %s",
                    minimumContentLength, maximumContentLength);
            throw new TextContentCreationException(errMsg, ErrorCode.UNACCEPTABLE_CONTENT_LENGTH);
        }
    }

    private ZonedDateTime getExpiryDate(String expiryDateStr) {
        ZonedDateTime dateTime = ZonedDateTime.parse(expiryDateStr);
        if (dateTime.isBefore(ZonedDateTime.now())) {
            throw new TextContentCreationException("Expiry date is in the past", ErrorCode.INVALID_EXPIRY_DATE);
        }
        return dateTime;
    }

    private TextDto mapTextDto(Text text) {
        TextDto textDto = new TextDto();
        textDto.setContent(text.getContent());
        textDto.setHasExpiryDate(text.isHasExpiryDate());
        if (textDto.isHasExpiryDate()) {
            textDto.setExpiryDate(text.getExpiryDate().format(DateTimeFormatter.ISO_OFFSET_DATE_TIME));
        }
        return textDto;
    }

    private String generateReference(String contentStart) {
        int retryLimit = 5;
        String reference = null;
        for (int i = 0; i <= retryLimit; i++) {
            String tempRef = generateHash(contentStart);
            Optional<Text> textOptional = textRepository.findByReference(tempRef);
            if (textOptional.isEmpty()) {
                reference = tempRef;
                break;
            }
        }

        if (reference == null) {
            throw new RuntimeException("Failed to generate unique reference");
        }

        return reference;
    }

    private String generateHash(String text) {
        text = text.concat(String.valueOf(System.currentTimeMillis()));
        return DigestUtils.md5DigestAsHex(text.concat(text).getBytes()).substring(0, 7);
    }
}
