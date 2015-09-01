package de.koehler;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Mongo repository for jms text messages
 */
@Component
public interface MessageRepository extends MongoRepository<Message, String> {
}
