package ru.kupchinolabs.vertx.vertx_hydra_2020.destination;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Handler;
import io.vertx.core.eventbus.Message;

import java.util.logging.Logger;

public class DestinationConnector extends AbstractVerticle {

    Logger logger = Logger.getLogger(DestinationConnector.class.getSimpleName());

    @Override
    public void start() {
        vertx.eventBus().consumer("destination_topic", (Handler<Message<String>>) m -> logger.info("Receiving '" + m.body() + "'"));
        logger.info("<--- DestinationConnector ---> is now running");
    }

    @Override
    public void stop() {
        logger.info("<--- DestinationConnector ---> is now stopped");
    }

}
