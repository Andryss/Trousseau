package ru.itmo.trousseau.repository;

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
public class BookingRepositoryImpl implements BookingRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final RowMapper<Item> mapper = new BeanPropertyRowMapper<>(Item.class);

    @Override
    public List<Item> findAllByUserId(long userId) {
        MapSqlParameterSource params = new MapSqlParameterSource("userId", userId);
        return jdbcTemplate.query("""
            select * from find_booked_items(:userId)
            """, params, mapper);
    }

    @Override
    public long countByUserId(long userId) {
        MapSqlParameterSource params = new MapSqlParameterSource("userId", userId);
        //noinspection DataFlowIssue
        return jdbcTemplate.queryForObject("""
            select count(*) from bookings where user_id = :userId
            """, params, Long.class);
    }

    @Override
    public long findUserIdByBookedItem(long itemId) {
        MapSqlParameterSource params = new MapSqlParameterSource("itemId", itemId);
        //noinspection DataFlowIssue
        return jdbcTemplate.queryForObject("""
            select user_id from bookings where item_id = :itemId
            """, params, Long.class);
    }

    @Override
    public void bookItem(long itemId, long userId) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("itemId", itemId);
        params.addValue("userId", userId);
        jdbcTemplate.update("""
            call book_item(:itemId, :userId)
            """, params);
    }

    @Override
    public void closeItem(long itemId, long userId) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("itemId", itemId);
        params.addValue("userId", userId);
        jdbcTemplate.update("""
            call close_item(:itemId, :userId)
            """, params);
    }

    @Override
    public void cancelBooking(long itemId, long userId) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("itemId", itemId);
        params.addValue("userId", userId);
        jdbcTemplate.update("""
            call cancel_booking(:itemId, :userId)
            """, params);
    }
}
