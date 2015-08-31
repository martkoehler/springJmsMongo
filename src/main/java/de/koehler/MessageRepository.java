package de.koehler;

import org.springframework.data.mongodb.repository.MongoRepository;

import javax.jms.TextMessage;
import java.util.List;

/**
 * Mongo repository for jms text messages
 */
public interface MessageRepository extends MongoRepository<TextMessage, String> {
    @Override
    public <S extends TextMessage> S save(S s);

    @Override
    public List<TextMessage> findAll();
}
