package ru.itmo.trousseau.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.itmo.trousseau.model.Photo;

@Repository
public class PhotoRepositoryImpl implements PhotoRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final RowMapper<Photo> mapper;

    private final String selectByIdQuery;


    public PhotoRepositoryImpl(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.mapper = new BeanPropertyRowMapper<>(Photo.class);
        this.selectByIdQuery = """
            select * from photos where id = :id
            """;
    }

    @Override
    public Optional<Photo> findById(long id) {
        MapSqlParameterSource params = new MapSqlParameterSource("id", id);
        List<Photo> dormitories = jdbcTemplate.query(selectByIdQuery, params, mapper);
        if (dormitories.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(dormitories.get(0));
    }
}