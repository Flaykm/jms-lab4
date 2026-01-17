package com.example.rest_lab3.config;

import com.example.rest_lab3.jms.AuditLogListener;
import com.example.rest_lab3.jms.EmailNotificationListener;
import jakarta.jms.ConnectionFactory;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.listener.DefaultMessageListenerContainer;

import jakarta.jms.Topic;

@Configuration
public class JmsConfig {

    // Topic для всех изменений
    @Bean
    public Topic changeTopic() {
        return new ActiveMQTopic("entity-changes-topic");
    }

    // ActiveMQ ConnectionFactory
    @Bean
    public ConnectionFactory connectionFactory() {
        return new ActiveMQConnectionFactory("tcp://localhost:61616");
    }

    // JMS Template для отправки сообщений
    @Bean
    public JmsTemplate jmsTemplate(ConnectionFactory connectionFactory, Topic changeTopic) {
        JmsTemplate template = new JmsTemplate(connectionFactory);
        template.setDefaultDestination(changeTopic);
        template.setPubSubDomain(true); // включаем Topic mode
        return template;
    }

    // Listener для логирования изменений (Audit)
    @Bean
    public DefaultMessageListenerContainer auditListenerContainer(
            ConnectionFactory connectionFactory,
            AuditLogListener listener,
            Topic changeTopic
    ) {
        DefaultMessageListenerContainer container = new DefaultMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setDestination(changeTopic);
        container.setMessageListener(listener);
        container.setPubSubDomain(true);
        return container;
    }

    // Listener для email уведомлений
    @Bean
    public DefaultMessageListenerContainer emailListenerContainer(
            ConnectionFactory connectionFactory,
            EmailNotificationListener listener,
            Topic changeTopic
    ) {
        DefaultMessageListenerContainer container = new DefaultMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setDestination(changeTopic);
        container.setMessageListener(listener);
        container.setPubSubDomain(true);
        return container;
    }
}
