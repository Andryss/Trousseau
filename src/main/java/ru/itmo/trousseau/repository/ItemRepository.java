package ru.itmo.trousseau.repository;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import ru.itmo.trousseau.model.Item;

public interface ItemRepository {
    long save(String title, long photoId, String description, long userId, Timestamp creationDatetime);
    Optional<Item> findById(long id);
    List<Item> findAllBookedBy(long userId);
    List<Item> findAllOwnedBy(long userId);
    List<Item> findAllSavedBy(long userId);
    List<Item> findAllBySearch(String query, String[] categories);
    void bookItem(long id, long userId);
    void closeItem(long id, long userId);
}
