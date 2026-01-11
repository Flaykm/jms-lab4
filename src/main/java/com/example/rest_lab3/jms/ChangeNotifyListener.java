package com.example.rest_lab3.jms;

import com.example.rest_lab3.email.EmailNotifier;
import com.example.rest_lab3.model.Book;
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
            if (!(message instanceof MapMessage)) {
                logger.warn("Received unsupported message type: {}", message.getClass());
                return; // не кидаем исключение, чтобы не уходило в DLQ
            }

            MapMessage mapMessage = (MapMessage) message;

            String entityName = mapMessage.getString("entityName");
            long entityId = mapMessage.getLong("entityId");
            String changeType = mapMessage.getString("changeType");

            logger.info("Received JMS message: entity={}, id={}, type={}", entityName, entityId, changeType);

            // Если это книга и есть поля title и year
            if ("Book".equals(entityName)) {
                String title = mapMessage.getString("title");
                int year = mapMessage.getInt("year");

                logger.info("Book details: title='{}', year={}", title, year);

                // Отправка email, если год >= 2027
                if (year >= 2027) {
                    String subject = "Ошибка: некорректный год выпуска книги";
                    String body = "Книга: " + title +
                            "\nID: " + entityId +
                            "\nТип изменения: " + changeType +
                            "\nГод выпуска: " + year;
                    emailNotifier.sendEmail(subject, body);
                    logger.info("Email notification sent for Book {}", entityId);
                }
            }

        } catch (Exception e) {
            // Логируем исключение, но не кидаем его дальше
            logger.error("Failed to process JMS message", e);
        }
    }
}
