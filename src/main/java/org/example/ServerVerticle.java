package org.example;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.Promise;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.eventbus.Message;
import io.vertx.core.eventbus.MessageConsumer;

public class ServerVerticle extends AbstractVerticle {

    EventBus eb = vertx.eventBus();

    @Override
    public void start(Promise<Void> startPromise) throws Exception {
        System.out.println("Server is starting...");
        startPromise
                .future()
                .compose(this::receiveMsgHandler);
    }

    // receive message from sender
    public Future<Void> receiveMsgHandler(Void v) {
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
