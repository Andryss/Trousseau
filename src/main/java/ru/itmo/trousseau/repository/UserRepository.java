package ru.itmo.trousseau.repository;

import java.util.Optional;

import ru.itmo.trousseau.model.User;

public interface UserRepository {
    Optional<User> findById(long id);
    Optional<User> findByLoginIgnoreCase(String login);
}
