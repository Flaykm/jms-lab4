package com.example.rest_lab3.jms;

import com.example.rest_lab3.model.Book;
import com.example.rest_lab3.model.ChangeLog;
import com.example.rest_lab3.repository.ChangeLogRepository;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class ChangeEventProducer {

    private final JmsTemplate jmsTemplate;
    private final ChangeLogRepository changeLogRepository;

    public ChangeEventProducer(JmsTemplate jmsTemplate, ChangeLogRepository changeLogRepository) {
        this.jmsTemplate = jmsTemplate;
        this.changeLogRepository = changeLogRepository;
    }

    public void sendChange(String entityName, Long entityId, String changeType, String details) {
        // JMS
        jmsTemplate.convertAndSend("change-queue", Map.of(
                "entityName", entityName,
                "entityId", entityId,
                "changeType", changeType,
                "details", details
        ));

        // Логирование в базу
        ChangeLog log = new ChangeLog(entityName, entityId, changeType, details);
        changeLogRepository.save(log);
    }

    public void sendChange(Book book, String changeType) {
        sendChange("Book", book.getId(), changeType, "Title: " + book.getTitle() + ", Year: " + book.getYear());
    }
}
