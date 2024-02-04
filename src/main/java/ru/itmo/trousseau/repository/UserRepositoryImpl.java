package ru.itmo.trousseau.repository;

import java.util.List;
import java.util.Optional;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.itmo.trousseau.model.User;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final RowMapper<User> mapper = new BeanPropertyRowMapper<>(User.class);

    @Override
    public Optional<User> findById(long id) {
        MapSqlParameterSource params = new MapSqlParameterSource("id", id);
        List<User> users = jdbcTemplate.query("""
            select * from users where id = :id
            """, params, mapper);
        if (users.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(users.get(0));
    }

    @Override
    public Optional<User> findByLoginIgnoreCase(String login) {
        MapSqlParameterSource params = new MapSqlParameterSource("login", login);
        List<User> users = jdbcTemplate.query("""
            select * from users where lower(login) = lower(:login)
            """, params, mapper);
        if (users.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(users.get(0));
    }
}
