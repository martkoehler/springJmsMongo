package de.koehler;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.springframework.data.annotation.Id;

import static org.apache.commons.lang3.Validate.notNull;

/**
 * Representation of a jms message for mongo db
 */
public class Message {
    @Id
    private String id;

    private String message;

    private long timestamp;

    public static Message create(final String message, final long timestamp) {
        return new Message(message, timestamp);
    }

    private Message(final String message, final long timestamp) {
        this.message = notNull(message, "message must not be null");
        this.timestamp = notNull(timestamp, "timestamp must not be null");
    }

    public String getMessage() {
        return message;
    }

    public long getTimestamp() {
        return timestamp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        Message message1 = (Message) o;

        return new EqualsBuilder()
                .append(timestamp, message1.timestamp)
                .append(id, message1.id)
                .append(message, message1.message)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(id)
                .append(message)
                .append(timestamp)
                .toHashCode();
    }
}
