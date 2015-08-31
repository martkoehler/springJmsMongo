package de.koehler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.config.JmsListenerContainerFactory;
import org.springframework.jms.config.SimpleJmsListenerContainerFactory;
import org.springframework.stereotype.Component;

import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.TextMessage;

/**
 * Text message consumer
 */
@Component
public class MessageConsumer {
    private static final Logger LOG = LoggerFactory.getLogger(MessageConsumer.class);

    @Autowired
    private MessageRepository repository;

    @Bean
    public JmsListenerContainerFactory<?> defaultJmsContainerFactory(ConnectionFactory connectionFactory) {
        SimpleJmsListenerContainerFactory factory = new SimpleJmsListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        return factory;
    }

    @JmsListener(destination = "${destination}", containerFactory = "defaultJmsContainerFactory")
    public void receive(TextMessage message) throws JMSException {
        LOG.info("Received message: " + message.getText());
        repository.save(Message.create(message.getText(), message.getJMSTimestamp()));

    }
}
