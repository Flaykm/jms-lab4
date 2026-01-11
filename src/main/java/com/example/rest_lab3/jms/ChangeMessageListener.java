package com.example.rest_lab3.email;

import com.example.rest_lab3.model.Book;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class ChangeMessageListener {

    private final EmailNotifier emailNotifier;

    public ChangeMessageListener(EmailNotifier emailNotifier) {
        this.emailNotifier = emailNotifier;
    }

    @JmsListener(destination = "change-queue")
    public void receiveMessage(Map<String, Object> payload) {
        String entityName = (String) payload.get("entityName");
        Long entityId = ((Number) payload.get("entityId")).longValue();
        String changeType = (String) payload.get("changeType");

        Object detailsObj = payload.get("details");
        if ("Book".equals(entityName) && detailsObj instanceof Book book) {
            if (book.getYear() >= 2027) {
                emailNotifier.sendEmail(
                        "Ошибка: некорректный год",
                        "Книга: " + book.getTitle() +
                                "\nID: " + entityId +
                                "\nТип изменения: " + changeType +
                                "\nГод выпуска: " + book.getYear()
                );
            }
        }
    }
}
