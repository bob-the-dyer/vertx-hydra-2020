package ru.kupchinolabs.vertx.vertx_hydra_2020.destination;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Context;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.eventbus.Message;
import lombok.extern.slf4j.Slf4j;
import ru.kupchinolabs.vertx.vertx_hydra_2020.cluster_classloader.VertxClusterByteCodeStorage;
import ru.kupchinolabs.vertx.vertx_hydra_2020.cluster_classloader.VertxDeploymentManagerHacker;

@Slf4j
public class DestinationConnector extends AbstractVerticle {

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
        vertx.eventBus().consumer("destination_topic", (Handler<Message<String>>) m -> log.info("Receiving '" + m.body() + "'"));
        log.info("<--- DestinationConnector ---> is now running");
    }

    @Override
    public void stop() {
        log.info("<--- DestinationConnector ---> is now stopped");
    }

}
