package ru.itmo.trousseau.service;

import java.util.List;
import java.util.Optional;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.itmo.trousseau.exception.NotFoundException;
import ru.itmo.trousseau.model.Item;
import ru.itmo.trousseau.messages.SearchRequest;
import ru.itmo.trousseau.repository.ItemRepository;

@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {

    private final ItemRepository itemRepository;

    @Override
    public Item findById(long id) {
        Optional<Item> itemOptional = itemRepository.findById(id);
        return itemOptional.orElseThrow(() -> new NotFoundException(String.valueOf(id)));
    }

    @Override
    public List<Item> findAllBookedBy(long userId) {
        return itemRepository.findAllBookedBy(userId);
    }

    @Override
    public List<Item> findAllWithSearch(SearchRequest search) {
        return itemRepository.findAllBySearch(search.getQuery(), search.getCategories());
    }
}
