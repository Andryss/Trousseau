package ru.itmo.trousseau.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.itmo.trousseau.model.Item;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ItemRepositoryImpl implements ItemRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final RowMapper<Item> mapper = new BeanPropertyRowMapper<>(Item.class);

    @Override
    public long save(String title, long photoId, String description, long userId, Timestamp creationDatetime) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("title", title);
        params.addValue("photoId", photoId);
        params.addValue("description", description);
        params.addValue("userId", userId);
        params.addValue("creationDatetime", creationDatetime);
        //noinspection DataFlowIssue,ConstantConditions
        return jdbcTemplate.queryForObject("""
            select * from insert_item(:title, :photoId, :description, :userId, :creationDatetime)
            """, params, Long.class);
    }

    @Override
    public Optional<Item> findById(long id) {
        MapSqlParameterSource params = new MapSqlParameterSource("id", id);
        List<Item> items = jdbcTemplate.query("""
            select * from items where id = :id
            """, params, mapper);
        if (items.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(items.get(0));
    }

    @Override
    public List<Item> findAllByUserId(long userId) {
        MapSqlParameterSource params = new MapSqlParameterSource("userId", userId);
        return jdbcTemplate.query("""
            select * from items where user_id = :userId
            """, params, mapper);
    }

    @Override
    public List<Item> findAllBySearch(String query, String[] categories) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("query", query);
        params.addValue("categories", categories);
        return jdbcTemplate.query("""
            select * from find_items(:query, :categories)
            """, params, mapper);
    }
}
