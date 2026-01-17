package com.example.rest_lab3.jms;

import com.example.rest_lab3.model.ChangeLog;
import com.example.rest_lab3.repository.ChangeLogRepository;
import jakarta.jms.MapMessage;
import jakarta.jms.Message;
import jakarta.jms.MessageListener;
import jakarta.jms.JMSException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class AuditLogListener implements MessageListener {

    private final ChangeLogRepository changeLogRepository;
    private final Logger logger = LoggerFactory.getLogger(AuditLogListener.class);

    public AuditLogListener(ChangeLogRepository changeLogRepository) {
        this.changeLogRepository = changeLogRepository;
    }

    @Override
    public void onMessage(Message message) {
        try {
            if (!(message instanceof MapMessage mapMessage)) return;

            String entityName = mapMessage.getString("entityName");
            long entityId = mapMessage.getLong("entityId");
            String changeType = mapMessage.getString("changeType");
            String details = mapMessage.itemExists("details") ? mapMessage.getString("details") : "";

            ChangeLog log = new ChangeLog();
            log.setEntityName(entityName);
            log.setEntityId(entityId);
            log.setChangeType(changeType);
            log.setChangeDetails(details);

            changeLogRepository.save(log);
            logger.info("Saved change to AuditLog: {} {} {}", entityName, changeType, entityId);

        } catch (JMSException e) {
            logger.error("Failed to process JMS message in AuditLogListener", e);
        }
    }
}
