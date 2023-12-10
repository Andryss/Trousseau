package ru.itmo.trousseau.service;

import java.util.List;

import ru.itmo.trousseau.model.Item;
import ru.itmo.trousseau.messages.SearchRequest;

public interface ItemService {
    List<Item> findAllBookedBy(long userId);
    List<Item> findAllWithSearch(SearchRequest search);
}
