package com.example.rest_lab3.jms;

import com.example.rest_lab3.email.EmailNotifier;
import jakarta.jms.MapMessage;
import jakarta.jms.Message;
import jakarta.jms.MessageListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class ChangeNotifyListener implements MessageListener {

    private final EmailNotifier emailNotifier;
    private final Logger logger = LoggerFactory.getLogger(ChangeNotifyListener.class);

    public ChangeNotifyListener(EmailNotifier emailNotifier) {
        this.emailNotifier = emailNotifier;
    }

    @Override
    public void onMessage(Message message) {
        try {
            if (!(message instanceof MapMessage mapMessage)) return;

            String entityName = mapMessage.getString("entityName");
            long entityId = mapMessage.getLong("entityId");

            if ("Book".equals(entityName) && mapMessage.itemExists("year")) {
                int year = mapMessage.getInt("year");
                String title = mapMessage.getString("title");

                if (year >= 2027) {
                    String subject = "Ошибка: некорректный год выпуска книги";
                    String body = "Книга: " + title +
                            "\nID: " + entityId +
                            "\nГод выпуска: " + year;

                    emailNotifier.sendEmail("flaykmax@gmail.com", subject, body);
                    logger.info("Email notification sent for Book {}", entityId);
                }
            }

        } catch (Exception e) {
            logger.error("Failed to process JMS message", e);
        }
    }
}
