package ru.itmo.trousseau.repository;

import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.itmo.trousseau.model.Item;

@Repository
public class BookingRepositoryImpl implements BookingRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final RowMapper<Item> mapper;

    private final String selectByUserIdQuery;
    private final String countByUserIdQuery;
    private final String selectUserIdByBookedItemQuery;
    private final String bookItemQuery;
    private final String closeItemQuery;
    private final String cancelBookingQuery;


    public BookingRepositoryImpl(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.mapper = new BeanPropertyRowMapper<>(Item.class);
        this.selectByUserIdQuery = """
            select * from find_booked_items(:userId)
            """;
        this.countByUserIdQuery = """
            select count(*) from bookings where user_id = :userId
            """;
        this.selectUserIdByBookedItemQuery = """
            select user_id from bookings where item_id = :itemId
            """;
        this.bookItemQuery = """
            call book_item(:itemId, :userId)
            """;
        this.closeItemQuery = """
            call close_item(:itemId, :userId)
            """;
        this.cancelBookingQuery = """
            call cancel_booking(:itemId, :userId)
            """;
    }

    @Override
    public List<Item> findAllByUserId(long userId) {
        MapSqlParameterSource params = new MapSqlParameterSource("userId", userId);
        return jdbcTemplate.query(selectByUserIdQuery, params, mapper);
    }

    @Override
    public long countByUserId(long userId) {
        MapSqlParameterSource params = new MapSqlParameterSource("userId", userId);
        //noinspection DataFlowIssue
        return jdbcTemplate.queryForObject(countByUserIdQuery, params, Long.class);
    }

    @Override
    public long findUserIdByBookedItem(long itemId) {
        MapSqlParameterSource params = new MapSqlParameterSource("itemId", itemId);
        //noinspection DataFlowIssue
        return jdbcTemplate.queryForObject(selectUserIdByBookedItemQuery, params, Long.class);
    }

    @Override
    public void bookItem(long itemId, long userId) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("itemId", itemId);
        params.addValue("userId", userId);
        jdbcTemplate.update(bookItemQuery, params);
    }

    @Override
    public void closeItem(long itemId, long userId) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("itemId", itemId);
        params.addValue("userId", userId);
        jdbcTemplate.update(closeItemQuery, params);
    }

    @Override
    public void cancelBooking(long itemId, long userId) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("itemId", itemId);
        params.addValue("userId", userId);
        jdbcTemplate.update(cancelBookingQuery, params);
    }
}
