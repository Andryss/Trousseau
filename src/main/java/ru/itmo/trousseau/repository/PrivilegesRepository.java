package ru.itmo.trousseau.repository;

import java.util.List;

public interface PrivilegesRepository {
    List<String> findAllForUser(long userId);
}
