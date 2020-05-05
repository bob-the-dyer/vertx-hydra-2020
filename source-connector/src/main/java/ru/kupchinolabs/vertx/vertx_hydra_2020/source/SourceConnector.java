package ru.kupchinolabs.vertx.vertx_hydra_2020.source;

import io.vertx.core.AbstractVerticle;

import java.util.logging.Logger;

public class SourceConnector extends AbstractVerticle {

    Logger logger = Logger.getLogger(SourceConnector.class.getSimpleName());

    static int counter = 0;

    @Override
    public void start() {
        vertx.setPeriodic(2500, c -> {
            String message = "vert.x " + counter++;
            logger.info("Publishing '" + message + "'");
            vertx.eventBus().publish("source_topic", message);
        });
        logger.info("<--- SourceConnector ---> is now running");
    }

    @Override
    public void stop() {
        logger.info("<--- SourceConnector ---> is now stopped");
    }

}
