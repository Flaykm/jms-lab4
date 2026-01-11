package com.example.rest_lab3.jms;

import com.example.rest_lab3.model.Book;
import jakarta.jms.MapMessage;
import jakarta.jms.Queue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

@Component
public class ChangeMessageSender {

    private final JmsTemplate jmsTemplate;
    private final Queue changeQueue;
    private final Logger logger = LoggerFactory.getLogger(ChangeMessageSender.class);

    public ChangeMessageSender(JmsTemplate jmsTemplate, Queue changeQueue) {
        this.jmsTemplate = jmsTemplate;
        this.changeQueue = changeQueue;
    }

    // Метод для Author / простых изменений
    public void sendChange(String entityName, Long entityId) {
        sendChange(entityName, entityId, "", "");
    }

    public void sendChange(String entityName, Long entityId, String changeType, String details) {
        jmsTemplate.send(changeQueue, session -> {
            MapMessage msg = session.createMapMessage();
            msg.setString("entityName", entityName);
            msg.setLong("entityId", entityId);
            msg.setString("changeType", changeType);
            msg.setString("details", details);
            return msg;
        });
        logger.info("Sent JMS change message: {} {} {}", entityName, changeType, entityId);
    }

    // Метод для Book
    public void sendChange(Book book, String changeType) {
        sendChange("Book", book.getId(), changeType, book.toString());
    }
}
