package ru.itmo.trousseau.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.itmo.trousseau.model.Item;
import ru.itmo.trousseau.model.Subscription;

@Slf4j
@Service
public class NotificationServiceImpl implements NotificationService {
    @Override
    public void notify(Subscription subscription, Item item) {
        log.info("Notification was sent to user {} (subscription {}, item {})",
                subscription.getUserId(), subscription.getId(), item.getId());
    }
}
