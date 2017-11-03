package com.github.csabaujvari.akkasample.rest;

import akka.event.LoggingAdapter;
import akka.http.javadsl.marshallers.jackson.Jackson;
import akka.http.javadsl.model.StatusCodes;
import akka.http.javadsl.server.AllDirectives;
import akka.http.javadsl.server.Route;
import com.github.csabaujvari.akkasample.AkkaApplication;
import com.github.csabaujvari.akkasample.rest.dto.Item;
import com.github.csabaujvari.akkasample.service.ItemService;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

import static akka.http.javadsl.server.PathMatchers.longSegment;

public class ItemController extends AllDirectives {

    private static final LoggingAdapter LOG = AkkaApplication.getSystem().log();
    private final ItemService itemService;

    public ItemController() {
        itemService = new ItemService();
    }

    public Route router() {
        return get(() ->
                pathPrefix("item", () ->
                        path(longSegment(), (Long id) -> {
                            LOG.info("/item/{} called", id);
                            final CompletionStage<Optional<Item>> futureMaybeItem
                                    = CompletableFuture.completedFuture(itemService.fetchItem(id));
                            return onSuccess(() -> futureMaybeItem, maybeItem ->
                                    maybeItem
                                            .map(item -> {
                                                LOG.info("Item retrieved successfully: {}", item);
                                                return completeOK(item, Jackson.marshaller());
                                            })
                                            .orElseGet(() -> {
                                                LOG.info("Couldn't retrieve item with id: {}", id);
                                                return complete(StatusCodes.NOT_FOUND, "Not Found");
                                            }));
                        })));
    }
}
