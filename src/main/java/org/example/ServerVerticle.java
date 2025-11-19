package org.example;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;

public class ServerVerticle extends AbstractVerticle {
    @Override
    public void start(Promise<Void> startPromise) throws Exception{
        System.out.println("Server is starting...");
        startPromise.complete();
    }

    // receive message from sender
    public void receiveMsg() {

    }

    // send message to recipient
    public void sendMsg() {

    }
}
