package de.koehler;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.Date;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;

/**
 * Test for {@link Message}
 */
@RunWith(JUnit4.class)
public class MessageTest {

    @Test
    public void create() {
        long timestamp = new Date().getTime();
        String messageString = "message";
        Message message = Message.create(messageString, timestamp);

        assertThat(message, notNullValue());
        assertThat(message.getMessage(), is(messageString));
        assertThat(message.getTimestamp(), is(timestamp));
    }
}
