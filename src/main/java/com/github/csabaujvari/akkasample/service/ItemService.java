package com.github.csabaujvari.akkasample.service;

import com.github.csabaujvari.akkasample.rest.dto.Item;

import java.util.Optional;

public class ItemService {

    public Optional<Item> fetchItem(long itemId) {
        return Optional.of(new Item("foo", itemId));
    }

}
