package ru.itmo.trousseau.repository;

import java.util.Optional;

import ru.itmo.trousseau.model.Photo;

public interface PhotoRepository {
    Optional<Photo> findById(long id);
}
