package com.example.rest_lab3.jms;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class ChangeLogListener {

    private final Logger logger = LoggerFactory.getLogger(ChangeLogListener.class);

    public void logChange(String entityName, Long entityId, String changeType, String details) {
        logger.info("Logging change: entity={}, id={}, type={}, details={}", entityName, entityId, changeType, details);
    }
}
