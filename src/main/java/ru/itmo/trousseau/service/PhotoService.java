package ru.itmo.trousseau.service;

import ru.itmo.trousseau.model.Photo;

public interface PhotoService {
    Photo findById(long id);
}
