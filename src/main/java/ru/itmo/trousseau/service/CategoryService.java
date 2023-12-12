package ru.itmo.trousseau.service;

import java.util.List;

import ru.itmo.trousseau.model.CategoryWithGroup;

public interface CategoryService {
    List<CategoryWithGroup> findAll();
    List<CategoryWithGroup> findAllByItemId(long itemId);
}
