package de.koehler;

import de.flapdoodle.embed.mongo.MongodExecutable;
import de.flapdoodle.embed.mongo.MongodStarter;
import de.flapdoodle.embed.mongo.config.IMongodConfig;
import de.flapdoodle.embed.mongo.config.MongodConfigBuilder;
import de.flapdoodle.embed.mongo.config.Net;
import de.flapdoodle.embed.mongo.config.Storage;
import de.flapdoodle.embed.mongo.distribution.Version;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.IOException;

/**
 * An embedded persistent mongo server
 */
@Component
public class MongoServer {

    private MongodExecutable mongodExecutable;

    @Value("${spring.data.mongodb.port}")
    private Integer port;

    @Value("${databaseDir}")
    private String databaseDir;

    @PostConstruct
    public void init() throws IOException {
        MongodStarter starter = MongodStarter.getDefaultInstance();

        final Storage replication = new Storage(databaseDir, null, 0);
        final IMongodConfig mongodConfig = new MongodConfigBuilder()
                .version(Version.Main.PRODUCTION)
                .replication(replication)
                .net(new Net(port, false))
                .build();

        mongodExecutable = starter.prepare(mongodConfig);
        mongodExecutable.start();
    }

    @PreDestroy
    public void close() {
        if (mongodExecutable != null) {
            mongodExecutable.stop();
        }
    }
}
