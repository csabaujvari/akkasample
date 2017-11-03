package com.github.csabaujvari.akkasample.router;

import akka.http.javadsl.server.AllDirectives;
import akka.http.javadsl.server.Route;
import com.github.csabaujvari.akkasample.rest.ItemController;
import com.github.csabaujvari.akkasample.rest.OrderController;

public class AkkaRouter extends AllDirectives {

    private final OrderController orderController;
    private final ItemController itemController;

    public AkkaRouter() {
        orderController = new OrderController();
        itemController = new ItemController();
    }

    public Route routes() {
        return route(
                itemController.router(),
                orderController.router()
        );
    }

}
