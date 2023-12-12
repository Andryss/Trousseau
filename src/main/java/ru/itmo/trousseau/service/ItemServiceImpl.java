package ru.itmo.trousseau.service;

import java.util.List;
import java.util.Optional;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.UncategorizedSQLException;
import org.springframework.stereotype.Service;
import ru.itmo.trousseau.exception.ForbiddenException;
import ru.itmo.trousseau.exception.NotFoundException;
import ru.itmo.trousseau.messages.SearchRequest;
import ru.itmo.trousseau.model.Item;
import ru.itmo.trousseau.model.User;
import ru.itmo.trousseau.repository.ItemRepository;
import ru.itmo.trousseau.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {

    private final ItemRepository itemRepository;
    private final UserRepository userRepository;

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
    public List<Item> findAllOwnedBy(long userId) {
        return itemRepository.findAllOwnedBy(userId);
    }

    @Override
    public List<Item> findAllSavedBy(long userId) {
        return itemRepository.findAllSavedBy(userId);
    }

    @Override
    public List<Item> findAllWithSearch(SearchRequest search) {
        return itemRepository.findAllBySearch(search.getQuery(), search.getCategories());
    }

    @Override
    public void bookItem(long itemId, String username) {
        Optional<User> userOptional = userRepository.findByLoginIgnoreCase(username);
        User user = userOptional.orElseThrow(() -> new NotFoundException(username));
        try {
            itemRepository.bookItem(itemId, user.getId());
        } catch (UncategorizedSQLException e) {
            throw new ForbiddenException(String.valueOf(itemId));
        }
    }

    @Override
    public void closeItem(long itemId, String username) {
        Optional<User> userOptional = userRepository.findByLoginIgnoreCase(username);
        User user = userOptional.orElseThrow(() -> new NotFoundException(username));
        try {
            itemRepository.closeItem(itemId, user.getId());
        } catch (UncategorizedSQLException e) {
            throw new ForbiddenException(String.valueOf(itemId));
        }
    }
}
