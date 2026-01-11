package com.example.rest_lab3.config;

import com.example.rest_lab3.jms.ChangeNotifyListener;
import jakarta.jms.Queue;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.listener.DefaultMessageListenerContainer;

import jakarta.jms.ConnectionFactory;
import org.apache.activemq.command.ActiveMQQueue;

@Configuration
public class JmsConfig {

    @Bean
    public Queue changeQueue() {
        return new ActiveMQQueue("change-queue");
    }

    @Bean
    public ConnectionFactory connectionFactory() {
        return new ActiveMQConnectionFactory("tcp://localhost:61616");
    }

    @Bean
    public JmsTemplate jmsTemplate(ConnectionFactory connectionFactory) {
        return new JmsTemplate(connectionFactory);
    }

    @Bean
    public DefaultMessageListenerContainer listenerContainer(
            ConnectionFactory connectionFactory,
            ChangeNotifyListener listener,
            Queue changeQueue) {
        DefaultMessageListenerContainer container = new DefaultMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setDestination(changeQueue);
        container.setMessageListener(listener);
        return container;
    }
}
