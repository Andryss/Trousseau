package ru.itmo.trousseau.service;

import java.util.List;

import ru.itmo.trousseau.model.Item;

public interface SavedItemsService {
    void addToSaved(String username, long itemId);
    void deleteFromSaved(String username, long itemId);

    List<Item> findAllSavedBy(long userId);
}
