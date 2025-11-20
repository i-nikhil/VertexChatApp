package org.example;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.Promise;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.json.JsonObject;

import java.time.LocalTime;
import java.util.UUID;

public class UserVerticle extends AbstractVerticle {

    String uuid = null;
    EventBus eventBus = null;

    @Override
    public void start(Promise<Void> startPromise) throws Exception {
        startPromise
                .future()
                .compose(this::setUserId)
                .compose(this::setEventBus)
                .compose(this::getMsg)
                .compose(this::sendMsg)
                .onComplete(handle -> {
                    if (handle.succeeded()) {
                        System.out.println("User Verticle started for user: " + uuid);
                    } else {
                        System.out.println("User Verticle failed to start for user: " + uuid);
                    }
                });
    }

    // we need event bus to communicate with server verticle
    public Future<EventBus> setEventBus(Void v) {
        eventBus = vertx.eventBus();
        System.out.println("EventBus initialized for user: " + uuid);
        return Future.future(p -> p.complete(eventBus));
    }

    // assign unique user ID
    public Future<Void> setUserId(Void v) {
        uuid = UUID.randomUUID().toString();
        System.out.println("User ID assigned: " + uuid);
        return Future.future(Promise::complete);
    }

    // receive message from server
    public Future<EventBus> getMsg(EventBus eb) {
        eb.consumer("SERVER_PROPAGATION_TOPIC", handle -> {
            System.out.println("@" + uuid + " received message: " + handle.body().toString());
        });
        return Future.future(p -> p.complete(eb));
    }

    // send message to server
    public Future<Void> sendMsg(EventBus eb) {
        String msg = "New Message ".concat(LocalTime.now().toString());
        JsonObject jo = new JsonObject();
        jo.put("Sender", uuid);
        jo.put("Message", msg);
        eb.send("SERVER", jo);
        return Future.future(Promise::complete);
    }
}
