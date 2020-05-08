package ru.kupchinolabs.vertx.vertx_hydra_2020.cluster_classloader;

import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.shareddata.AsyncMap;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.util.Base64;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

@Slf4j
public class VertxClusterAwareClassLoader extends ClassLoader {

    private final Vertx vertx;

    public VertxClusterAwareClassLoader(ClassLoader parent, Vertx vertx) {
        super(parent);
        this.vertx = vertx;
    }

    @SneakyThrows
    @Override
    public Class findClass(String name) {
        byte[] b = loadClassFromCluster(name);
        return defineClass(name, b, 0, b.length);
    }

    private byte[] loadClassFromCluster(String fileName) throws InterruptedException, ClassNotFoundException {

        log.info("trying to find class byte code for {}", fileName);

        AtomicReference<String> buffer = new AtomicReference<>();

        CountDownLatch latch = new CountDownLatch(1);

        String asyncMapWithClasses = "clustered-classes";

        vertx.sharedData().getAsyncMap(asyncMapWithClasses, (Handler<AsyncResult<AsyncMap<String, String>>>) mapEvent -> {
            if (mapEvent.succeeded()) {
                AsyncMap<String, String> map = mapEvent.result();
                map.get(fileName, valueEvent -> {
                    if (valueEvent.succeeded()) {
                        String byteCode = valueEvent.result();
                        log.info("classByteCode: {}", byteCode);
                        buffer.set(byteCode);
                        log.info("class byte code for {} loaded", fileName);
                        latch.countDown();
                    } else {
                        log.error("troubles getting class byte code for {}", fileName);
                    }
                });
            } else {
                log.error("troubles getting async map {}", asyncMapWithClasses);
            }
        });

        latch.await(5, TimeUnit.SECONDS);

        String base64byteCode = buffer.get();

        if (base64byteCode == null) {
            throw new ClassNotFoundException();
        }

        return Base64.getDecoder().decode(base64byteCode.getBytes());
    }

}
