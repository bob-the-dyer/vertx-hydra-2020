package ru.kupchinolabs.vertx.vertx_hydra_2020.poison_pill;

import io.vertx.core.AbstractVerticle;

import java.util.logging.Logger;

public class PoisonPill extends AbstractVerticle {

    Logger logger = Logger.getLogger(PoisonPill.class.getSimpleName());

    @Override
    public void start() {
        vertx.setTimer(5000, c -> {
            logger.info("<<< *** POISON PILL IS NOW ACTIVATED *** >>>");
            Runtime.getRuntime().halt(-42);
        });
        logger.info("<--- PoisonPill ---> is now running");
    }

    @Override
    public void stop() {
        logger.info("<--- PoisonPill ---> is now stopped");
    }

}
