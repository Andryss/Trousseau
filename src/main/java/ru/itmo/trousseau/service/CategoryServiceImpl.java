package ru.itmo.trousseau.service;

import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.itmo.trousseau.model.CategoryWithGroup;
import ru.itmo.trousseau.repository.CategoryRepository;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Override
    public List<CategoryWithGroup> findAll() {
        return categoryRepository.findAll();
    }

    @Override
    public List<CategoryWithGroup> findAllByItemId(long itemId) {
        return categoryRepository.findAllByItemId(itemId);
    }
}
