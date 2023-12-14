package ru.itmo.trousseau.repository;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import ru.itmo.trousseau.model.Item;

public interface ItemRepository {
    long save(String title, long photoId, String description, long userId, Timestamp creationDatetime);

    Optional<Item> findById(long id);
    List<Item> findAllByUserId(long userId);

    List<Item> findAllBySearch(String query, String[] categories);
}
