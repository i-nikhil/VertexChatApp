package org.example;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.Promise;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.eventbus.Message;

public class ServerVerticle extends AbstractVerticle {

    EventBus eb = null;

    @Override
    public void start(Promise<Void> startPromise) throws Exception {
        System.out.println("Server is starting...");
        eb = vertx.eventBus();
        receiveMsgHandler()
                .onComplete(handler -> {
                    if (handler.succeeded()) {
                        System.out.println("Server Verticle started successfully.");
                        startPromise.complete();
                    } else {
                        System.out.println("Server Verticle failed to start.");
                        startPromise.fail(handler.cause().getMessage());
                    }
                });
    }

    // receive message from sender
    public Future<Void> receiveMsgHandler() {
        eb.consumer("SERVER", this::sendMsg);
        System.out.println("Server is ready to receive messages.");
        return Future.future(Promise::complete);
    }

    // send message to recipient
    public void sendMsg(Message<Object> handler) {
        System.out.println("Server received message: " + handler.body().toString());
        eb.publish("SERVER_PROPAGATION_TOPIC", handler.body().toString());
    }
}
