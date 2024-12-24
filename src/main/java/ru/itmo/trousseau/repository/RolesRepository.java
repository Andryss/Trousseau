package ru.itmo.trousseau.repository;

import java.util.Map;

public interface RolesRepository {
    Map<Long, String> findAllUsersWithoutRoles();
    void saveUserRole(Long userId, String role);
}
