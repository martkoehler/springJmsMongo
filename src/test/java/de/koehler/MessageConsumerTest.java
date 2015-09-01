package de.koehler;

import org.apache.activemq.command.ActiveMQTextMessage;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.jms.config.JmsListenerContainerFactory;

import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import java.util.Date;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.mockito.Mockito.*;

/**
 * Test class for {@link MessageConsumer}
 */
@RunWith(MockitoJUnitRunner.class)
public class MessageConsumerTest {

    @InjectMocks
    private MessageConsumer consumer;

    @Mock
    private MessageRepository repository;


    @Test
    public void defaultJmsContainerFactory() {
        ConnectionFactory connectionFactory = mock(ConnectionFactory.class);
        JmsListenerContainerFactory<?> containerFactory = consumer.defaultJmsContainerFactory(connectionFactory);

        assertThat(containerFactory, notNullValue());
    }

    @Test(expected = NullPointerException.class)
    public void defaultJmsContainerFactoryNullCheck() {
        consumer.defaultJmsContainerFactory(null);
    }

    @Test
    public void receive() throws JMSException {
        ActiveMQTextMessage textMessage = new ActiveMQTextMessage();
        long timestamp = new Date().getTime();
        textMessage.setJMSTimestamp(timestamp);
        String message = "messageText";
        textMessage.setText(message);
        consumer.receive(textMessage);
        ArgumentCaptor<Message> captor = ArgumentCaptor.forClass(Message.class);

        verify(repository, times(1)).save(captor.capture());
        verifyNoMoreInteractions(repository);

        assertThat(Message.create(message, timestamp), is(captor.getValue()));
    }

    @Test(expected = NullPointerException.class)
    public void receiveNullCheck() throws JMSException {
        consumer.receive(null);
    }
}
