package ru.itmo.trousseau.repository;

import java.sql.Timestamp;
import java.util.List;

import ru.itmo.trousseau.model.Item;

public interface SavedItemsRepository {
    void save(long userId, long itemId, Timestamp addedDatetime);
    void delete(long userId, long itemId);

    List<Item> findAllByUserId(long userId);
    List<Long> findIdsByUserId(long userId);
}
