package ru.itmo.trousseau.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.itmo.trousseau.object.SearchCategory;
import ru.itmo.trousseau.object.SearchInfo;
import ru.itmo.trousseau.repository.CategoryRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SearchServiceImpl implements SearchService {

    private final CategoryRepository categoryRepository;

    @Override
    public SearchInfo createEmpty() {
        List<SearchCategory> searchCategories = categoryRepository.findAll().stream()
                .map(cwg -> new SearchCategory(cwg.getName(), cwg.getDescription(),
                        cwg.getGroupName(), cwg.getGroupDescription()))
                .toList();
        return new SearchInfo(null, searchCategories.toArray(new SearchCategory[0]));
    }
}
