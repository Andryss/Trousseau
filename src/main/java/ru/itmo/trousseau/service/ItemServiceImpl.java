package ru.itmo.trousseau.service;

import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.itmo.trousseau.model.Item;
import ru.itmo.trousseau.repository.ItemRepository;

@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {

    private final ItemRepository itemRepository;

    @Override
    public List<Item> findAllBookedBy(long userId) {
        return itemRepository.findAllBookedBy(userId);
    }
}
