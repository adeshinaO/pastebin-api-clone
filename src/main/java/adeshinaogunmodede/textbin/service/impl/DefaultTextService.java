package adeshinaogunmodede.textbin.service.impl;

import adeshinaogunmodede.textbin.api.dto.CreateTextDto;
import adeshinaogunmodede.textbin.api.dto.TextDto;
import adeshinaogunmodede.textbin.repository.TextRepository;
import adeshinaogunmodede.textbin.service.TextService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DefaultTextService implements TextService {

    private final TextRepository textRepository;

    @Autowired
    public DefaultTextService(TextRepository textRepository) {
        this.textRepository = textRepository;
    }

    // TODO:
    // - Finish this class
    // - Write unit tests for this class
    // - Write job that runs every week and deletes all expired texts (needed if they are never called)
    // - Write Unit test for the job.
    // - Write integration test for text controller.
    // - Write Dockerfile
    // - Write README
    // - Write

    @Override
    public TextDto createText(CreateTextDto createTextDto) {
        // TODO: Validate length using a config value
        // TODO:

        return null;
    }

    @Override
    public TextDto getText(String reference) {

        // todo: if expired, don't delete, just throw correct error. Allow job to do the deletion
        //          for performance.

        // todo: SQL for creating tables should have index on reference column.

        return null;
    }

    private String generateReference() {

        return "";
    }
}
