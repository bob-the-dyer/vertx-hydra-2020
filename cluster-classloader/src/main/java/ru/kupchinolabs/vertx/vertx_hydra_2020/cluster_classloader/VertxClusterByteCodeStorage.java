package ru.kupchinolabs.vertx.vertx_hydra_2020.cluster_classloader;

import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.shareddata.AsyncMap;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Slf4j
public class VertxClusterByteCodeStorage {

    @SneakyThrows
    public static void storeClass(Vertx vertx, Object object) {

        String asyncMapWithClasses = "clustered-classes";

        String className = object.getClass().getName();

        String classFile = className.replace('.', File.separatorChar) + ".class";

        InputStream stream = VertxClusterByteCodeStorage.class.getClassLoader().getResourceAsStream(classFile);

        if (stream == null) {
            log.info("file {} with class definition is not found on class path, probably it was loaded another way, i e via vert.x cluster", classFile);
            return;
        }

        String classByteCode = new String(Base64.getEncoder().encode(stream.readAllBytes()), StandardCharsets.UTF_8);

        log.info("classByteCode: {}", classByteCode);

        vertx.sharedData().getAsyncMap(asyncMapWithClasses, (Handler<AsyncResult<AsyncMap<String, String>>>) mapEvent -> {
            if (mapEvent.succeeded()) {
                AsyncMap<String, String> map = mapEvent.result();
                map.put(className, classByteCode, putEvent -> {
                    if (putEvent.succeeded()) {
                        log.info("class byte code for {} stored", className);
                    } else {
                        log.error("troubles putting class byte code for {}", className);
                    }
                });
            } else {
                log.error("troubles getting async map {}", asyncMapWithClasses);
            }
        });
    }

}
