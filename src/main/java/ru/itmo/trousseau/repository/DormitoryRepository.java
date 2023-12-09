package ru.itmo.trousseau.repository;

import java.util.Optional;

import ru.itmo.trousseau.model.Dormitory;

public interface DormitoryRepository {
    Optional<Dormitory> findById(long id);
}
