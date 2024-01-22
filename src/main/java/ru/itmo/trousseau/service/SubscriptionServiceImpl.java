package ru.itmo.trousseau.service;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.itmo.trousseau.exception.NotFoundException;
import ru.itmo.trousseau.messages.CreateSubscriptionRequest;
import ru.itmo.trousseau.model.User;
import ru.itmo.trousseau.object.SubscriptionDescription;
import ru.itmo.trousseau.repository.CategoryRepository;
import ru.itmo.trousseau.repository.SubscriptionRepository;
import ru.itmo.trousseau.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class SubscriptionServiceImpl implements SubscriptionService {

    private final UserRepository userRepository;
    private final SubscriptionRepository subscriptionRepository;
    private final CategoryRepository categoryRepository;

    @Override
    public void createSubscription(CreateSubscriptionRequest request, String username) {
        User user = userRepository.findByLoginIgnoreCase(username).orElseThrow(() -> new NotFoundException(username));

        long subId = subscriptionRepository.save(user.getId(), request.getName(), Timestamp.from(Instant.now()));

        categoryRepository.saveAllForSubscription(subId, request.getCategories());
    }

    @Override
    public List<SubscriptionDescription> findAllOwnedBy(long userId) {
        return subscriptionRepository.findAllByUserId(userId).stream()
                .map(s -> new SubscriptionDescription(
                        s.getName(),
                        categoryRepository.findAllBySubscriptionId(s.getId()),
                        s.getCreationDatetime()
                ))
                .toList();
    }
}
