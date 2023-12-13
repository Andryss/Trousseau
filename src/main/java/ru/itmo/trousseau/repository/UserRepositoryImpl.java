package ru.itmo.trousseau.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.itmo.trousseau.model.User;

@Repository
public class UserRepositoryImpl implements UserRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    private final String selectByIdQuery;
    private final String selectByLoginQuery;


    public UserRepositoryImpl(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.selectByIdQuery = """
            select * from users where id = :id
            """;
        this.selectByLoginQuery = """
            select * from users where lower(login) = lower(:login)
            """;
    }

    @Override
    public Optional<User> findById(long id) {
        MapSqlParameterSource params = new MapSqlParameterSource("id", id);
        List<User> users = jdbcTemplate.queryForList(selectByIdQuery, params, User.class);
        if (users.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(users.get(0));
    }

    @Override
    public Optional<User> findByLoginIgnoreCase(String login) {
        MapSqlParameterSource params = new MapSqlParameterSource("login", login);
        List<User> users = jdbcTemplate.queryForList(selectByLoginQuery, params, User.class);
        if (users.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(users.get(0));
    }
}
