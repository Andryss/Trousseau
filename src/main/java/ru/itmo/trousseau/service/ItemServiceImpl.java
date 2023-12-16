package ru.itmo.trousseau.service;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.itmo.trousseau.exception.BadRequestException;
import ru.itmo.trousseau.exception.NotFoundException;
import ru.itmo.trousseau.messages.CreateItemRequest;
import ru.itmo.trousseau.messages.SearchRequest;
import ru.itmo.trousseau.model.Item;
import ru.itmo.trousseau.model.Subscription;
import ru.itmo.trousseau.model.User;
import ru.itmo.trousseau.repository.CategoryRepository;
import ru.itmo.trousseau.repository.ItemRepository;
import ru.itmo.trousseau.repository.PhotoRepository;
import ru.itmo.trousseau.repository.SubscriptionRepository;
import ru.itmo.trousseau.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {

    private final NotificationService notificationService;

    private final ItemRepository itemRepository;
    private final UserRepository userRepository;
    private final PhotoRepository photoRepository;
    private final CategoryRepository categoryRepository;
    private final SubscriptionRepository subscriptionRepository;

    @Override
    public Item findById(long id) {
        Optional<Item> itemOptional = itemRepository.findById(id);
        return itemOptional.orElseThrow(() -> new NotFoundException(String.valueOf(id)));
    }

    @Override
    public List<Item> findAllOwnedBy(long userId) {
        return itemRepository.findAllByUserId(userId);
    }

    @Override
    public List<Item> findAllWithSearch(SearchRequest search) {
        return itemRepository.findAllBySearch(search.getQuery(), search.getCategories());
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

        categoryRepository.saveAllForItem(itemId, request.getCategories());

        List<Subscription> subs = subscriptionRepository.findAllByCategoriesIn(request.getCategories());
        Item item = itemRepository.findById(itemId).orElseThrow();
        subs.forEach(subscription -> notificationService.notify(subscription, item));
    }
}
