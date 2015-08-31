package de.koehler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Component;

import javax.jms.TextMessage;

/**
 * Text message producer
 */
@Component
public class MessageProducer {

    @Autowired
    private ConfigurableApplicationContext context;

    @Autowired
    private JmsTemplate template;

    @Value("${destination}")
    private String destination;

    public void send(final Message message) {

        final MessageCreator messageCreator = session -> {
            TextMessage textMessage = session.createTextMessage(message.getMessage());
            textMessage.setJMSTimestamp(message.getTimestamp());
            return textMessage;
        };

        template.send(destination, messageCreator);
    }
}
