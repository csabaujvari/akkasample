package com.github.csabaujvari.akkasample.service;

import akka.Done;
import akka.actor.ActorRef;
import com.github.csabaujvari.akkasample.AkkaApplication;
import com.github.csabaujvari.akkasample.actor.MessagePublisherActor;
import com.github.csabaujvari.akkasample.actor.protocol.AkkaActorProtocol;
import com.github.csabaujvari.akkasample.rest.dto.Order;

public class OrderService {

    private ActorRef actorRef;

    public OrderService() {
        actorRef = AkkaApplication.getSystem().actorOf(MessagePublisherActor.getProps(), MessagePublisherActor.NAME);
    }

    public Done saveOrder(final Order order) {
        actorRef.tell(new AkkaActorProtocol.PublishMessage(order.toString()), null);

        return Done.getInstance();
    }
}
