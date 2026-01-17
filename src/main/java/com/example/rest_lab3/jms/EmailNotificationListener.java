package com.example.rest_lab3.jms;

import com.example.rest_lab3.email.EmailNotifier;
import com.example.rest_lab3.model.Book;
import jakarta.jms.MapMessage;
import jakarta.jms.Message;
import jakarta.jms.MessageListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class EmailNotificationListener implements MessageListener {

    private final EmailNotifier emailNotifier;
    private final Logger logger = LoggerFactory.getLogger(EmailNotificationListener.class);

    @Value("${email.to}")
    private String notifyTo;

    public EmailNotificationListener(EmailNotifier emailNotifier) {
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
                    Book book = new Book();
                    book.setId(entityId);
                    book.setTitle(title);
                    book.setYear(year);


                    emailNotifier.sendEmail(
                            notifyTo,
                            "Ошибка: некорректный год книги",
                            "Книга: " + book.getTitle() +
                                    "\nID: " + book.getId() +
                                    "\nГод выпуска: " + book.getYear()
                    );

                    logger.info("Sent email notification for Book {}", entityId);
                }
            }
        } catch (Exception e) {
            logger.error("Failed to process JMS message in EmailNotificationListener", e);
        }
    }
}
