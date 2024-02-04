package ru.itmo.trousseau.repository;

import java.sql.Timestamp;
import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.itmo.trousseau.model.Item;

@Repository
@RequiredArgsConstructor
public class SavedItemsRepositoryImpl implements SavedItemsRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final RowMapper<Item> mapper = new BeanPropertyRowMapper<>(Item.class);

    @Override
    public List<Item> findAllByUserId(long userId) {
        MapSqlParameterSource params = new MapSqlParameterSource("userId", userId);
        return jdbcTemplate.query("""
            select * from find_saved_items(:userId)
            """, params, mapper);
    }

    @Override
    public List<Long> findIdsByUserId(long userId) {
        MapSqlParameterSource params = new MapSqlParameterSource("userId", userId);
        return jdbcTemplate.queryForList("""
            select id from find_saved_items(:userId)
            """, params, Long.class);
    }

    @Override
    public void save(long userId, long itemId, Timestamp addedDatetime) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("userId", userId);
        params.addValue("itemId", itemId);
        params.addValue("addedDatetime", addedDatetime);
        jdbcTemplate.update("""
            insert into saved_items values (:userId, :itemId, :addedDatetime)
            """, params);
    }

    @Override
    public void delete(long userId, long itemId) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("userId", userId);
        params.addValue("itemId", itemId);
        jdbcTemplate.update("""
            delete from saved_items where user_id = :userId and item_id = :itemId
            """, params);
    }
}
