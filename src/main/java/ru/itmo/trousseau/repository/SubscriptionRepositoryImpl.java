package ru.itmo.trousseau.repository;

import java.sql.Timestamp;
import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.itmo.trousseau.model.Subscription;

@Repository
@RequiredArgsConstructor
public class SubscriptionRepositoryImpl implements SubscriptionRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final RowMapper<Subscription> mapper = new BeanPropertyRowMapper<>(Subscription.class);

    @Override
    public long save(long userId, String name, Timestamp creationDatetime) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("userId", userId);
        params.addValue("name", name);
        params.addValue("creationDatetime", creationDatetime);
        //noinspection DataFlowIssue,ConstantConditions
        return jdbcTemplate.queryForObject("""
            select * from insert_subscription(:userId, :name, :creationDatetime)
            """, params, Long.class);
    }

    @Override
    public List<Subscription> findAllByUserId(long userId) {
        MapSqlParameterSource params = new MapSqlParameterSource("userId", userId);
        return jdbcTemplate.query("""
            select * from find_subscriptions(:userId)
            """, params, mapper);
    }

    @Override
    public List<Subscription> findAllByCategoriesIn(String[] categories) {
        MapSqlParameterSource params = new MapSqlParameterSource("categories", categories);
        return jdbcTemplate.query("""
            select * from find_covering_subscriptions(:categories)
            """, params, mapper);
    }
}
