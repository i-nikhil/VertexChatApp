package org.example;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.Promise;

public class MainVerticle extends AbstractVerticle {

    @Override
    public void start(Promise<Void> startPromise) throws Exception {
        System.out.println("Main Verticle started");

        initializeServerVerticle()
                .compose(this::initializeUserVerticle)
                .onComplete(handler -> {
                    if (handler.succeeded()) {
                        System.out.println("Main verticle deployment successful");
                        startPromise.complete();
                    } else {
                        System.out.println("Main verticle deployment failed");
                        startPromise.fail(handler.cause().getMessage());
                    }
                });
    }

    public Future<Void> initializeServerVerticle() {
        return vertx.deployVerticle(new ServerVerticle())
                .compose(id -> { //whenever we do compose, we return a Future
                    System.out.println("Server Verticle deployed with ID: " + id);
                    return Future.future(Promise::complete);
                });
    }

    public Future<Void> initializeUserVerticle(Void v) {
        return vertx.deployVerticle(new UserVerticle())
                .compose(id -> {
                    System.out.println("User Verticle deployed with ID: " + id);
                    return Future.future(Promise::complete);
                });
    }
}
