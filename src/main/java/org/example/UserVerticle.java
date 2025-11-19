package org.example;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.Promise;
import io.vertx.core.eventbus.EventBus;

import java.util.UUID;

public class UserVerticle extends AbstractVerticle {

    String uuid = null;
    EventBus eventBus = null;

    @Override
    public void start(Promise<Void> startPromise) throws Exception{
        startPromise
                .future()
                .compose(this::setUserId)
                .compose(this::setEventBus);
    }

    // we need event bus to communicate with server verticle
    public Future<EventBus> setEventBus(Void v) {
        eventBus = vertx.eventBus();
        System.out.println("EventBus initialized for user: " + uuid);
        return Future.future(p-> p.complete(eventBus));
    }

    // assign unique user ID
    public Future<Void> setUserId(Void v) {
        uuid = UUID.randomUUID().toString();
        System.out.println("User ID assigned: " + uuid);
        return Future.future(Promise::complete);
    }

    // receive message from server
    public void getMsg(EventBus eb) {

    }

    // send message to server
    public void sendMsg(EventBus eb) {

    }
}
