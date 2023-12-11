package ru.itmo.trousseau.service;

import ru.itmo.trousseau.model.User;

public interface UserService {
    User findById(long id);
    User findByUsername(String username);
}
