package ru.itmo.trousseau.repository;

import java.util.HashMap;
import java.util.Map;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class RolesRepositoryImpl implements RolesRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    @Override
    public Map<Long, String> findAllUsersWithoutRoles() {
        Map<Long, String> result = new HashMap<>();
        jdbcTemplate.query("""
                select u.id, u.role
                    from users u
                        left join user_roles ur on ur.user_id = u.id
                    where ur.user_id is null
                """, Map.of(), rs -> {
            result.put(rs.getLong("id"), rs.getString("role"));
        });
        return result;
    }

    @Override
    public void saveUserRole(Long userId, String role) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("userId", userId);
        params.addValue("roleName", role);
        jdbcTemplate.update("""
                insert into user_roles (user_id, role_id)
                    VALUES (:userId, (select id from roles where role = :roleName))
                """, params);
    }
}
