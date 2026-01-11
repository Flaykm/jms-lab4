package com.example.rest_lab3.email;

import com.example.rest_lab3.model.Book;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class EmailNotifier {

    private final JmsTemplate jmsTemplate;

    public EmailNotifier(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    public void sendChange(String entityName, Long entityId, String changeType, Book book) {
        Map<String, Object> payload = new HashMap<>();
        payload.put("entityName", entityName);
        payload.put("entityId", entityId);
        payload.put("changeType", changeType);
        payload.put("details", book);

        jmsTemplate.convertAndSend("change-queue", payload);
    }

    public void sendEmail(String subject, String body) {
        System.out.println("EMAIL\n" + subject + "\n" + body);
    }
}
