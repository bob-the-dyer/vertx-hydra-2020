package ru.kupchinolabs.vertx.vertx_hydra_2020.converter;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Handler;
import io.vertx.core.eventbus.Message;

import static java.lang.System.out;

public class Converter extends AbstractVerticle {
    @Override
    public void start() {
        vertx.eventBus().consumer("source_topic", (Handler<Message<String>>) message -> convert(message.body()));
        out.println("<--- Converter ---> is now running");
    }

    private void convert(String body) {
        String converted = new StringBuilder(body).reverse().toString();
        System.out.println("Converting '" + body + "' to '" + converted + "' and publishing");
        vertx.eventBus().publish("destination_topic", converted);
    }

    @Override
    public void stop() {
        out.println("<--- Converter ---> is now stopped");
    }

}
