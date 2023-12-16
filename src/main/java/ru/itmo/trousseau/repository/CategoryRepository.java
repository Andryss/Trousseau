package ru.itmo.trousseau.repository;

import ru.itmo.trousseau.model.CategoryWithGroup;

import java.util.List;

public interface CategoryRepository {
    List<CategoryWithGroup> findAll();
    List<CategoryWithGroup> findAllByItemId(long itemId);
    List<CategoryWithGroup> findAllBySubscriptionId(long subId);

    void saveAllForItem(long itemId, String[] categories);
    void saveAllForSubscription(long subId, String[] categories);
}
