package com.github.csabaujvari.akkasample.actor;

import akka.actor.AbstractActor;
import akka.actor.Actor;
import akka.actor.Props;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.japi.pf.ReceiveBuilder;
import com.github.csabaujvari.akkasample.actor.protocol.AkkaActorProtocol;

public class MessagePublisherActor extends AbstractActor implements Actor {

    public static final String NAME = "msg-publisher-actor";
    private static Props props = Props.create(MessagePublisherActor.class);
    private final LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);

    @Override
    public Receive createReceive() {
        return ReceiveBuilder.create()
                .match(AkkaActorProtocol.PublishMessage.class, msg -> {
                    log.info("Message published: " + msg);
                })
                .build();
    }

    public static Props getProps() {
        return props;
    }
}
