package org.example;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;

public class PersistenceVerticle extends AbstractVerticle {
    @Override
    public void start(Promise<Void> startPromise) throws Exception{
        startPromise.complete();
        System.out.println("Persistence Verticle started");
    }
}
