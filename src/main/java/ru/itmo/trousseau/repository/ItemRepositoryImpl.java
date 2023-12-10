package ru.itmo.trousseau.repository;

import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.itmo.trousseau.model.Item;

@Repository
public class ItemRepositoryImpl implements ItemRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final RowMapper<Item> mapper;

    private final String selectBookedByQuery;


    public ItemRepositoryImpl(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.mapper = new BeanPropertyRowMapper<>(Item.class);
        this.selectBookedByQuery = """
            select * from find_booked_items(:userId)
            """;
    }

    @Override
    public List<Item> findAllBookedBy(long userId) {
        MapSqlParameterSource params = new MapSqlParameterSource("userId", userId);
        return jdbcTemplate.query(selectBookedByQuery, params, mapper);
    }
}
