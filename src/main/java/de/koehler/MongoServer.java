package de.koehler;

import de.flapdoodle.embed.mongo.MongodExecutable;
import de.flapdoodle.embed.mongo.MongodStarter;
import de.flapdoodle.embed.mongo.config.IMongodConfig;
import de.flapdoodle.embed.mongo.config.MongodConfigBuilder;
import de.flapdoodle.embed.mongo.config.Net;
import de.flapdoodle.embed.mongo.config.Storage;
import de.flapdoodle.embed.mongo.distribution.Version;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger LOG = LoggerFactory.getLogger(MongoServer.class);

    private MongodExecutable mongodExecutable;

    @Value("${spring.data.mongodb.port}")
    private Integer port;

    @Value("${databaseDir}")
    private String databaseDir;

    @PostConstruct
    public void init() throws IOException {
        LOG.info("Prepare mongo server configuration");
        MongodStarter starter = MongodStarter.getDefaultInstance();

        final Storage replication = new Storage(databaseDir, null, 0);
        final IMongodConfig mongodConfig = new MongodConfigBuilder()
                .version(Version.Main.PRODUCTION)
                .replication(replication)
                .net(new Net(port, false))
                .build();

        mongodExecutable = starter.prepare(mongodConfig);

        LOG.info("Starting mongo server");
        mongodExecutable.start();
        LOG.info("Mongo server started");
    }

    @PreDestroy
    public void close() {
        if (mongodExecutable != null) {
            LOG.info("Stopping mongo server");
            mongodExecutable.stop();
            LOG.info("Mongo server stopped");
        }
    }
}
