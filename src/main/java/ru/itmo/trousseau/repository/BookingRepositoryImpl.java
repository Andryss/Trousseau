package ru.itmo.trousseau.repository;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class BookingRepositoryImpl implements BookingRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    private final String countByUserIdQuery;

    public BookingRepositoryImpl(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.countByUserIdQuery = """
            select count(*) from bookings where user_id = :userID
            """;
    }


    @Override
    public long countByUserId(long userId) {
        MapSqlParameterSource params = new MapSqlParameterSource("userId", userId);
        //noinspection DataFlowIssue
        return jdbcTemplate.queryForObject(countByUserIdQuery, params, Long.class);
    }
}
