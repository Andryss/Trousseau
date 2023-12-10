package ru.itmo.trousseau.service;

import java.util.List;

import ru.itmo.trousseau.model.Item;

public interface ItemService {
    List<Item> findAllBookedBy(long userId);
}
