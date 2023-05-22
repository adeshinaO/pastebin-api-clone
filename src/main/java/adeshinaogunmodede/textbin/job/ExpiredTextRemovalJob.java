package adeshinaogunmodede.textbin.job;

import adeshinaogunmodede.textbin.model.Text;
import adeshinaogunmodede.textbin.repository.TextRepository;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class ExpiredTextRemovalJob {

    private static final Logger LOGGER = LoggerFactory.getLogger(ExpiredTextRemovalJob.class);
    private final TextRepository textRepository;
    private final int deletionLimit;

    @Autowired
    public ExpiredTextRemovalJob(
            TextRepository textRepository,
            @Value("${jobs.expired_text_removal.limit:100}") int deletionLimit) {
        this.textRepository = textRepository;
        this.deletionLimit = deletionLimit;
    }

    // POSSIBLE IMPROVEMENT: USE SHEDLOCK
    @Scheduled(cron = "${jobs.expired_text_removal.schedule:0 0 12 * * ?}")
    public void removeExpiredText() {
        LOGGER.info("Starting Job To Delete Expired Texts....");
        String currentDateTimeStr = ZonedDateTime.now().format(DateTimeFormatter.ISO_OFFSET_DATE_TIME);
        List<Text> texts = textRepository.fetchExpiredTexts(currentDateTimeStr, deletionLimit);
        LOGGER.info(String.format("Deleting %s records from texts table", texts.size()));
        textRepository.deleteAll(texts);
    }
}
