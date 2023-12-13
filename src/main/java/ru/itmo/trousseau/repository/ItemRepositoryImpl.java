package ru.itmo.trousseau.repository;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.itmo.trousseau.model.Item;

@Repository
public class ItemRepositoryImpl implements ItemRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    private final String insertQuery;
    private final String selectLastIdQuery;
    private final String selectByIdQuery;
    private final String selectBookedByQuery;
    private final String selectOwnedByQuery;
    private final String selectAllBySearchQuery;
    private final String selectSavedByQuery;
    private final String bookItemQuery;
    private final String closeItemQuery;


    public ItemRepositoryImpl(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.insertQuery = """
            insert into items (title, photo_id, description, status, user_id, creation_datetime) values
                (:title, :photoId, :description, 'ACTIVE', :userId, :creationDatetime)
            """;
        this.selectLastIdQuery = """
            select last_value as id from items_id_seq
            """;
        this.selectByIdQuery = """
            select * from items where id = :id
            """;
        this.selectBookedByQuery = """
            select * from find_booked_items(:userId)
            """;
        this.selectOwnedByQuery = """
            select * from items where user_id = :userId
            """;
        this.selectAllBySearchQuery = """
            select * from find_items(:query, :categories)
            """;
        this.selectSavedByQuery = """
            select * from find_saved_items(:userId)
            """;
        this.bookItemQuery = """
            call book_item(:id, :userId)
            """;
        this.closeItemQuery = """
            call close_item(:id, :userId)
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
        jdbcTemplate.update(insertQuery, params);
        //noinspection DataFlowIssue
        return jdbcTemplate.queryForObject(selectLastIdQuery, Map.of(), Long.class);
    }

    @Override
    public Optional<Item> findById(long id) {
        MapSqlParameterSource params = new MapSqlParameterSource("id", id);
        List<Item> items = jdbcTemplate.queryForList(selectByIdQuery, params, Item.class);
        if (items.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(items.get(0));
    }

    @Override
    public List<Item> findAllBookedBy(long userId) {
        MapSqlParameterSource params = new MapSqlParameterSource("userId", userId);
        return jdbcTemplate.queryForList(selectBookedByQuery, params, Item.class);
    }

    @Override
    public List<Item> findAllOwnedBy(long userId) {
        MapSqlParameterSource params = new MapSqlParameterSource("userId", userId);
        return jdbcTemplate.queryForList(selectOwnedByQuery, params, Item.class);
    }

    @Override
    public List<Item> findAllSavedBy(long userId) {
        MapSqlParameterSource params = new MapSqlParameterSource("userId", userId);
        return jdbcTemplate.queryForList(selectSavedByQuery, params, Item.class);
    }

    @Override
    public List<Item> findAllBySearch(String query, String[] categories) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("query", query);
        params.addValue("categories", categories);
        return jdbcTemplate.queryForList(selectAllBySearchQuery, params, Item.class);
    }

    @Override
    public void bookItem(long id, long userId) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", id);
        params.addValue("userId", userId);
        jdbcTemplate.update(bookItemQuery, params);
    }

    @Override
    public void closeItem(long id, long userId) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", id);
        params.addValue("userId", userId);
        jdbcTemplate.update(closeItemQuery, params);
    }
}
