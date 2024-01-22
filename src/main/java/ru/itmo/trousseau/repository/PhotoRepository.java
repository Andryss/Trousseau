package ru.itmo.trousseau.repository;

import java.sql.Timestamp;
import java.util.Optional;

import ru.itmo.trousseau.model.Photo;

public interface PhotoRepository {
    long save(byte[] data, Timestamp uploadDatetime);

    Optional<Photo> findById(long id);
}
