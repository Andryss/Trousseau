package ru.itmo.trousseau.service;

import java.util.List;

import ru.itmo.trousseau.messages.CreateItemRequest;
import ru.itmo.trousseau.model.Item;
import ru.itmo.trousseau.messages.SearchRequest;

public interface ItemService {
    Item findById(long id);
    List<Item> findAllBookedBy(long userId);
    List<Item> findAllOwnedBy(long userId);
    List<Item> findAllSavedBy(long userId);
    List<Item> findAllWithSearch(SearchRequest search);
    void bookItem(long itemId, String username);
    void closeItem(long itemId, String username);
    void createItem(CreateItemRequest request, String username);
}
