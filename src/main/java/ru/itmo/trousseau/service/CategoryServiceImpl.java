package ru.itmo.trousseau.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.itmo.trousseau.model.Category;
import ru.itmo.trousseau.model.CategoryWithGroup;
import ru.itmo.trousseau.object.CategoryGroupDescription;
import ru.itmo.trousseau.repository.CategoryRepository;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Override
    public List<CategoryGroupDescription> findAll() {
        Map<Long, CategoryGroupDescription> groups = new HashMap<>();
        categoryRepository.findAll().forEach(cwg ->
                groups.computeIfAbsent(cwg.getGroupId(), id ->
                                new CategoryGroupDescription(cwg.getGroupName(), cwg.getGroupDescription(), new ArrayList<>()))
                        .getCategories().add(new Category(cwg.getId(), cwg.getName(), cwg.getDescription(), cwg.getGroupId())));
        return new ArrayList<>(groups.values());
    }

    @Override
    public List<CategoryWithGroup> findAllByItemId(long itemId) {
        return categoryRepository.findAllByItemId(itemId);
    }
}
