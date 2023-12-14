package ru.itmo.trousseau.repository;

import java.util.List;

import ru.itmo.trousseau.model.Item;

public interface BookingRepository {
    List<Item> findAllByUserId(long userId);
    long countByUserId(long userId);

    long findUserIdByBookedItem(long itemId);

    void bookItem(long itemId, long userId);
    void closeItem(long itemId, long userId);
    void cancelBooking(long itemId, long userId);
}
