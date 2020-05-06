package ru.kupchinolabs.vertx.vertx_hydra_2020.destination;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Handler;
import io.vertx.core.eventbus.Message;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DestinationConnector extends AbstractVerticle {

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
