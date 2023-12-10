package ru.itmo.trousseau.repository;

import java.util.List;

import ru.itmo.trousseau.model.Item;

public interface ItemRepository {
    List<Item> findAllBookedBy(long userId);
}
