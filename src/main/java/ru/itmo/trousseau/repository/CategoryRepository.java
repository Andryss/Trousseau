package ru.itmo.trousseau.repository;

import ru.itmo.trousseau.model.CategoryWithGroup;

import java.util.List;

public interface CategoryRepository {
    List<CategoryWithGroup> findAll();
}
