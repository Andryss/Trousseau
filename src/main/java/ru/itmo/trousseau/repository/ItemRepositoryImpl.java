package ru.itmo.trousseau.repository;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

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

    private final String insertQuery;
    private final String selectByIdQuery;
    private final String selectOwnedByQuery;
    private final String selectAllBySearchQuery;


    public ItemRepositoryImpl(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.mapper = new BeanPropertyRowMapper<>(Item.class);
        this.insertQuery = """
            select * from insert_item(:title, :photoId, :description, 'ACTIVE', :userId, :creationDatetime)
            """;
        this.selectByIdQuery = """
            select * from items where id = :id
            """;
        this.selectOwnedByQuery = """
            select * from items where user_id = :userId
            """;
        this.selectAllBySearchQuery = """
            select * from find_items(:query, :categories)
            """;
    }

    @Override
    public long save(String title, long photoId, String description, long userId, Timestamp creationDatetime) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("title", title);
        params.addValue("photoId", photoId);
        params.addValue("description", description);
        params.addValue("userId", userId);
        params.addValue("creationDatetime", creationDatetime);
        //noinspection DataFlowIssue
        return jdbcTemplate.queryForObject(insertQuery, params, Long.class);
    }

    @Override
    public Optional<Item> findById(long id) {
        MapSqlParameterSource params = new MapSqlParameterSource("id", id);
        List<Item> items = jdbcTemplate.query(selectByIdQuery, params, mapper);
        if (items.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(items.get(0));
    }

    @Override
    public List<Item> findAllByUserId(long userId) {
        MapSqlParameterSource params = new MapSqlParameterSource("userId", userId);
        return jdbcTemplate.query(selectOwnedByQuery, params, mapper);
    }

    @Override
    public List<Item> findAllBySearch(String query, String[] categories) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("query", query);
        params.addValue("categories", categories);
        return jdbcTemplate.query(selectAllBySearchQuery, params, mapper);
    }
}
