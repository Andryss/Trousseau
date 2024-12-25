package ru.itmo.trousseau.repository;

import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class PrivilegesRepositoryImpl implements PrivilegesRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    @Override
    public List<String> findAllForUser(long userId) {
        MapSqlParameterSource params = new MapSqlParameterSource("userId", userId);
        return jdbcTemplate.queryForList("""
                select distinct p.privilege from users u
                    join user_roles ur on ur.user_id = u.id
                    join roles r on ur.role_id = r.id
                    join role_privileges rp on rp.role_id = r.id
                    join privileges p on rp.privilege_id = p.id
                where u.id = :userId
                """, params, String.class);
    }
}
