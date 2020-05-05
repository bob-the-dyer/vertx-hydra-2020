package ru.kupchinolabs.vertx.vertx_hydra_2020.converter;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Handler;
import io.vertx.core.eventbus.Message;

import java.util.logging.Logger;

public class Converter extends AbstractVerticle {

    Logger logger = Logger.getLogger(Converter.class.getSimpleName());

    @Override
    public void start() {
        vertx.eventBus().consumer("source_topic", (Handler<Message<String>>) message -> convert(message.body()));
        logger.info("<--- Converter ---> is now running");
    }

    private void convert(String body) {
        String converted = new StringBuilder(body).reverse().toString();
        logger.info("Converting '" + body + "' to '" + converted + "'");
        vertx.eventBus().publish("destination_topic", converted);
    }

    @Override
    public void stop() {
        logger.info("<--- Converter ---> is now stopped");
    }

}
