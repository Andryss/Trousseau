package ru.itmo.trousseau.repository;

import java.util.List;
import java.util.Optional;

import ru.itmo.trousseau.model.Item;

public interface ItemRepository {
    Optional<Item> findById(long id);
    List<Item> findAllBookedBy(long userId);
    List<Item> findAllBySearch(String query, String[] categories);
}
