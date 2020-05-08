package ru.kupchinolabs.vertx.vertx_hydra_2020.source;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Context;
import io.vertx.core.Vertx;
import lombok.extern.slf4j.Slf4j;
import ru.kupchinolabs.vertx.vertx_hydra_2020.cluster_classloader.VertxClusterByteCodeStorage;
import ru.kupchinolabs.vertx.vertx_hydra_2020.cluster_classloader.VertxDeploymentManagerHacker;

@Slf4j
public class SourceConnector extends AbstractVerticle {

    static int counter = 0;

    @Override
    public void init(Vertx vertx, Context context) {
        super.init(vertx, context);
        log.info("init called");

// TODO uncomment to enable cluster aware classloading
//        VertxClusterByteCodeStorage.storeClass(vertx, this);
//        VertxDeploymentManagerHacker.hack(vertx);

    }

    @Override
    public void start() {
        vertx.setPeriodic(2500, c -> {
            String message = "vert.x " + counter++;
            log.info("Publishing '" + message + "'");
            vertx.eventBus().publish("source_topic", message);
        });
        log.info("<--- SourceConnector ---> is now running");
    }

    @Override
    public void stop() {
        log.info("<--- SourceConnector ---> is now stopped");
    }

}
