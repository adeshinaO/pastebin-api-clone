package adeshinaogunmodede.textbin.job;

import adeshinaogunmodede.textbin.repository.TextRepository;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class ExpiredTextRemovalJob {

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
        String currentDateTimeStr = ZonedDateTime.now().format(DateTimeFormatter.ISO_OFFSET_DATE_TIME);
        textRepository.deleteExpiredText(currentDateTimeStr, deletionLimit);
    }
}
