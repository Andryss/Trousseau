package ru.itmo.trousseau.service;

import java.util.List;

import ru.itmo.trousseau.model.Item;

public interface BookingService {
    List<Item> findAllBookedBy(long userId);

    void bookItem(long itemId, String username);
    void closeItem(long itemId, String username);
    void cancelBooking(long itemId, String username);
}
