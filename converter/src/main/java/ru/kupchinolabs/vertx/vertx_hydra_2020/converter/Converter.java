package ru.kupchinolabs.vertx.vertx_hydra_2020.converter;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Handler;
import io.vertx.core.eventbus.Message;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Converter extends AbstractVerticle {

    @Override
    public void start() {
        vertx.eventBus().consumer("source_topic", (Handler<Message<String>>) message -> convert(message.body()));
        log.info("<--- Converter ---> is now running");
    }

    private void convert(String body) {
        String converted = new StringBuilder(body).reverse().toString();
        log.info("Converting '" + body + "' to '" + converted + "'");
        vertx.eventBus().publish("destination_topic", converted);
    }

    @Override
    public void stop() {
        log.info("<--- Converter ---> is now stopped");
    }

}
