package ru.kupchinolabs.vertx.vertx_hydra_2020.destination;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Handler;
import io.vertx.core.eventbus.Message;

import static java.lang.System.out;

public class DestinationConnector extends AbstractVerticle {
    @Override
    public void start() {
        vertx.eventBus().consumer("destination_topic", (Handler<Message<String>>) m -> System.out.println("Receiving '" + m.body() + "'"));
        out.println("<--- DestinationConnector ---> is now running");
    }

    @Override
    public void stop() {
        out.println("<--- DestinationConnector ---> is now stopped");
    }

}
