package ru.itmo.trousseau.repository;

import java.sql.Timestamp;
import java.util.List;

import ru.itmo.trousseau.model.Subscription;

public interface SubscriptionRepository {
    long save(long userId, String name, Timestamp creationDatetime);

    List<Subscription> findAllByUserId(long userId);
}
