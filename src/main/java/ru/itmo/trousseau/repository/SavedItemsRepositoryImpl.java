package ru.itmo.trousseau.repository;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.itmo.trousseau.model.Item;

@Repository
public class SavedItemsRepositoryImpl implements SavedItemsRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final RowMapper<Item> mapper;

    private final String selectByUserIdQuery;
    private final String selectIdsByUserIdQuery;
    private final String insertQuery;
    private final String deleteQuery;


    public SavedItemsRepositoryImpl(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.mapper = new BeanPropertyRowMapper<>(Item.class);
        this.selectByUserIdQuery = """
            select * from find_saved_items(:userId)
            """;
        this.selectIdsByUserIdQuery = """
            select id from find_saved_items(:userId)
            """;
        this.insertQuery = """
            insert into saved_items values (:userId, :itemId, :addedDatetime)
            """;
        this.deleteQuery = """
            delete from saved_items where user_id = :userId and item_id = :itemId
            """;
    }

    @Override
    public List<Item> findAllByUserId(long userId) {
        MapSqlParameterSource params = new MapSqlParameterSource("userId", userId);
        return jdbcTemplate.query(selectByUserIdQuery, params, mapper);
    }

    @Override
    public List<Long> findIdsByUserId(long userId) {
        MapSqlParameterSource params = new MapSqlParameterSource("userId", userId);
        return jdbcTemplate.queryForList(selectIdsByUserIdQuery, params, Long.class);
    }

    @Override
    public void save(long userId, long itemId, Timestamp addedDatetime) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("userId", userId);
        params.addValue("itemId", itemId);
        params.addValue("addedDatetime", addedDatetime);
        jdbcTemplate.update(insertQuery, params);
    }

    @Override
    public void delete(long userId, long itemId) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("userId", userId);
        params.addValue("itemId", itemId);
        jdbcTemplate.update(deleteQuery, params);
    }
}
