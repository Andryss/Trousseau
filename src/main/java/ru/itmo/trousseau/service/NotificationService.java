package ru.itmo.trousseau.service;

import ru.itmo.trousseau.model.Item;
import ru.itmo.trousseau.model.Subscription;

public interface NotificationService {
    void notify(Subscription subscription, Item item);
}
