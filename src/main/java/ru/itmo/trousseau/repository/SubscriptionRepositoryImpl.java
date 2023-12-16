package ru.itmo.trousseau.repository;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.itmo.trousseau.model.Subscription;

@Repository
public class SubscriptionRepositoryImpl implements SubscriptionRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final RowMapper<Subscription> mapper;

    private final String insertQuery;
    private final String selectByUserIdQuery;


    public SubscriptionRepositoryImpl(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.mapper = new BeanPropertyRowMapper<>(Subscription.class);
        this.insertQuery = """
            select * from insert_subscription(:userId, :name, :creationDatetime)
            """;
        this.selectByUserIdQuery = """
            select * from find_subscriptions(:userId)
            """;
    }

    @Override
    public long save(long userId, String name, Timestamp creationDatetime) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("userId", userId);
        params.addValue("name", name);
        params.addValue("creationDatetime", creationDatetime);
        //noinspection DataFlowIssue
        return jdbcTemplate.queryForObject(insertQuery, params, Long.class);
    }

    @Override
    public List<Subscription> findAllByUserId(long userId) {
        MapSqlParameterSource params = new MapSqlParameterSource("userId", userId);
        return jdbcTemplate.query(selectByUserIdQuery, params, mapper);
    }
}
