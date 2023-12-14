package ru.itmo.trousseau.service;

import java.util.List;

import ru.itmo.trousseau.messages.CreateItemRequest;
import ru.itmo.trousseau.model.Item;
import ru.itmo.trousseau.messages.SearchRequest;

public interface ItemService {
    void createItem(CreateItemRequest request, String username);

    Item findById(long id);
    List<Item> findAllOwnedBy(long userId);
    List<Item> findAllWithSearch(SearchRequest search);
}
