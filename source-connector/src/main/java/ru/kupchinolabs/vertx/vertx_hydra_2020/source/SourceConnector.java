package ru.kupchinolabs.vertx.vertx_hydra_2020.source;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Context;
import io.vertx.core.Vertx;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SourceConnector extends AbstractVerticle {

    static int counter = 0;

    @Override
    public void init(Vertx vertx, Context context) {
        super.init(vertx, context);
        log.info("init called");
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
