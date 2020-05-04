package ru.kupchinolabs.vertx.vertx_hydra_2020.source;

import io.vertx.core.AbstractVerticle;

import static java.lang.System.out;

public class SourceConnector extends AbstractVerticle {

    static int counter = 0;

    @Override
    public void start() {
        vertx.setPeriodic(5000, c -> {
            String message = "vert.x " + counter++;
            out.println("publishing '" + message + "'");
            vertx.eventBus().publish("source_topic", message);
        });
        out.println("<--- SourceConnector ---> is now running");
    }

    @Override
    public void stop() {
        out.println("<--- SourceConnector ---> is now stopped");
    }

}
