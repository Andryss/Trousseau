package ru.itmo.trousseau.repository;

public interface BookingRepository {
    long countByUserId(long userId);
}
