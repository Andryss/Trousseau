package ru.itmo.trousseau.service;

import java.util.List;

import ru.itmo.trousseau.model.CategoryWithGroup;
import ru.itmo.trousseau.object.CategoryGroupDescription;

public interface CategoryService {
    List<CategoryGroupDescription> findAll();
    List<CategoryWithGroup> findAllByItemId(long itemId);
}
