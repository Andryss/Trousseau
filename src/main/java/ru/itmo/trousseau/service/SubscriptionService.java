package ru.itmo.trousseau.service;

import java.util.List;

import ru.itmo.trousseau.messages.CreateSubscriptionRequest;
import ru.itmo.trousseau.object.SubscriptionDescription;

public interface SubscriptionService {
    void createSubscription(CreateSubscriptionRequest request, String username);

    List<SubscriptionDescription> findAllOwnedBy(long userId);
}
