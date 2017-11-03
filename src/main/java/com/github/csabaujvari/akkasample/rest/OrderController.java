package com.github.csabaujvari.akkasample.rest;

import akka.Done;
import akka.event.LoggingAdapter;
import akka.http.javadsl.marshallers.jackson.Jackson;
import akka.http.javadsl.server.AllDirectives;
import akka.http.javadsl.server.Route;
import akka.http.scaladsl.model.StatusCode;
import com.github.csabaujvari.akkasample.AkkaApplication;
import com.github.csabaujvari.akkasample.rest.dto.Order;
import com.github.csabaujvari.akkasample.service.OrderService;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

public class OrderController extends AllDirectives {

    private static final LoggingAdapter LOG = AkkaApplication.getSystem().log();
    private final OrderService orderService;

    public OrderController() {
        orderService = new OrderService();
    }

    public Route router() {
        return post(() ->
                path("create-order", () ->
                        entity(Jackson.unmarshaller(Order.class), order -> {
                            LOG.info("/create-order called");
                            CompletionStage<Done> futureSaved
                                    = CompletableFuture.completedFuture(orderService.saveOrder(order));
                            return onSuccess(() -> futureSaved, done -> {
                                        LOG.info("Order creation successfully done");
                                        return complete(StatusCode.int2StatusCode(201));
                                    }
                            );
                        })));
    }

}
