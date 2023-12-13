package ru.itmo.trousseau.service;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.itmo.trousseau.exception.BadRequestException;
import ru.itmo.trousseau.exception.ForbiddenException;
import ru.itmo.trousseau.exception.NotFoundException;
import ru.itmo.trousseau.messages.CreateItemRequest;
import ru.itmo.trousseau.messages.SearchRequest;
import ru.itmo.trousseau.model.Item;
import ru.itmo.trousseau.model.Status;
import ru.itmo.trousseau.model.User;
import ru.itmo.trousseau.repository.BookingRepository;
import ru.itmo.trousseau.repository.CategoryRepository;
import ru.itmo.trousseau.repository.ItemRepository;
import ru.itmo.trousseau.repository.PhotoRepository;
import ru.itmo.trousseau.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {

    private final ItemRepository itemRepository;
    private final UserRepository userRepository;
    private final BookingRepository bookingRepository;
    private final PhotoRepository photoRepository;
    private final CategoryRepository categoryRepository;

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
        User user = userRepository.findByLoginIgnoreCase(username).orElseThrow(() -> new NotFoundException(username));

        if (bookingRepository.countByUserId(user.getId()) >= 3) {
            throw new ForbiddenException("already 3");
        }

        Item item = itemRepository.findById(itemId).orElseThrow(() -> new NotFoundException(String.valueOf(itemId)));

        if (item.getStatus() != Status.ACTIVE) {
            throw new ForbiddenException(item.getStatus().name());
        }
        if (item.getUserId().equals(user.getId())) {
            throw new ForbiddenException("owner");
        }

        itemRepository.bookItem(itemId, user.getId());
    }

    @Override
    public void closeItem(long itemId, String username) {
        User user = userRepository.findByLoginIgnoreCase(username).orElseThrow(() -> new NotFoundException(username));
        Item item = itemRepository.findById(itemId).orElseThrow(() -> new NotFoundException(String.valueOf(itemId)));

        if (item.getStatus() != Status.BLOCKED) {
            throw new ForbiddenException(item.getStatus().name());
        }
        if (!item.getUserId().equals(user.getId())) {
            throw new ForbiddenException("not owner");
        }

        itemRepository.closeItem(itemId, user.getId());
    }

    @Override
    public void createItem(CreateItemRequest request, String username) {
        User user = userRepository.findByLoginIgnoreCase(username).orElseThrow(() -> new NotFoundException(username));

        byte[] data;
        try {
            data = request.getPhoto().getBytes();
        } catch (IOException e) {
            throw new BadRequestException("photo");
        }
        long photoId = photoRepository.save(data, Timestamp.from(Instant.now()));

        long itemId = itemRepository.save(request.getTitle(), photoId, request.getDescription(),
                user.getId(), Timestamp.from(Instant.now()));

        categoryRepository.saveAll(itemId, request.getCategories());
    }
}
