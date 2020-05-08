package ru.kupchinolabs.vertx.vertx_hydra_2020.cluster_classloader;

import io.vertx.core.*;
import io.vertx.core.impl.DeploymentManager;
import io.vertx.core.impl.HAManager;
import io.vertx.core.impl.VertxImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.reflect.FieldUtils;

import java.util.function.Supplier;

@Slf4j
public class VertxDeploymentManagerHacker {

    public static void hack(Vertx vertx) {

        VertxImpl vertxImpl = (VertxImpl) vertx;
        HAManager haManager = ((VertxImpl) vertx).haManager();

        DeploymentManager hackedDeploymentManager = new DeploymentManager(vertxImpl) {
            @Override
            public void deployVerticle(String identifier, DeploymentOptions options, Handler<AsyncResult<String>> completionHandler) {
                VertxClusterAwareClassLoader vertxClusterAwareClassLoader =
                        new VertxClusterAwareClassLoader(this.getClass().getClassLoader(), vertx);

                ClassLoader oldContextClassLoader = Thread.currentThread().getContextClassLoader();
                Thread.currentThread().setContextClassLoader(vertxClusterAwareClassLoader);

                log.info("setting context class loader to vertxClusterAwareClassLoader");

                super.deployVerticle(identifier, options, completionHandler);

                log.info("reverting context class loader to oldContextClassLoader");

                Thread.currentThread().setContextClassLoader(oldContextClassLoader);
            }

            @Override
            public void deployVerticle(Supplier<Verticle> verticleSupplier, DeploymentOptions options, Handler<AsyncResult<String>> completionHandler) {
                VertxClusterAwareClassLoader vertxClusterAwareClassLoader =
                        new VertxClusterAwareClassLoader(this.getClass().getClassLoader(), vertx);

                ClassLoader oldContextClassLoader = Thread.currentThread().getContextClassLoader();
                Thread.currentThread().setContextClassLoader(vertxClusterAwareClassLoader);

                log.info("setting context class loader to vertxClusterAwareClassLoader");

                super.deployVerticle(verticleSupplier, options, completionHandler);

                log.info("reverting context class loader to oldContextClassLoader");

                Thread.currentThread().setContextClassLoader(oldContextClassLoader);
            }
        };

        try {
            log.info("hacking deploymentManager with custom deploymentManager");
            FieldUtils.writeField(haManager, "deploymentManager", hackedDeploymentManager, true);
        } catch (IllegalAccessException e) {
            log.error("IllegalAccessException", e);
        }
    }
}
