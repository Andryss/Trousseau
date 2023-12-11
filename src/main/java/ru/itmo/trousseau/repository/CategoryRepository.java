package ru.itmo.trousseau.repository;

import ru.itmo.trousseau.model.CategoryWithGroup;

import java.util.List;

public interface CategoryRepository {
    List<CategoryWithGroup> findAll();
    List<CategoryWithGroup> findAllByItemId(long id);
}
