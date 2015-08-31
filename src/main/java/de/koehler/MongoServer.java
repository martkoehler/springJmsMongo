package de.koehler;

import de.flapdoodle.embed.mongo.MongodExecutable;
import de.flapdoodle.embed.mongo.MongodProcess;
import de.flapdoodle.embed.mongo.MongodStarter;
import de.flapdoodle.embed.mongo.config.IMongodConfig;
import de.flapdoodle.embed.mongo.config.MongodConfigBuilder;
import de.flapdoodle.embed.mongo.config.Net;
import de.flapdoodle.embed.mongo.distribution.Version;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;

/**
 * Created by mart on 31.08.15.
 */
@Component
public class MongoServer {

    @PostConstruct
    public void init() throws IOException {
        MongodStarter starter = MongodStarter.getDefaultInstance();

        int port = 12345;
        IMongodConfig mongodConfig = new MongodConfigBuilder()
                .version(Version.Main.PRODUCTION)
                .net(new Net(port, false))
                .build();

        MongodExecutable mongodExecutable = null;
        try {
            mongodExecutable = starter.prepare(mongodConfig);
            MongodProcess mongod = mongodExecutable.start();
/*
            MongoClient mongo = new MongoClient("localhost", port);
            DB db = mongo.getDB("test");
            DBCollection col = db.createCollection("testCol", new BasicDBObject());
            col.save(new BasicDBObject("testDoc", new Date()));*/

        } finally {
            /*if (mongodExecutable != null)
                mongodExecutable.stop();*/
        }
    }
}
