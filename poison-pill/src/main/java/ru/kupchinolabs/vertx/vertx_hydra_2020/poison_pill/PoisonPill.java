package ru.kupchinolabs.vertx.vertx_hydra_2020.poison_pill;

import io.vertx.core.AbstractVerticle;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PoisonPill extends AbstractVerticle {

    @Override
    public void start() {
        vertx.setTimer(10000, c -> {
            log.info("<<< \u2620\u2620\u2620 POISON PILL IS NOW ACTIVATED \u2620\u2620\u2620 >>>");
            Runtime.getRuntime().halt(-42);
        });
        log.info("<--- PoisonPill ---> is now running");
    }

    @Override
    public void stop() {
        log.info("<--- PoisonPill ---> is now stopped");
    }

}
