package ru.itmo.trousseau.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.itmo.trousseau.model.Dormitory;

@Repository
public class DormitoryRepositoryImpl implements DormitoryRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final RowMapper<Dormitory> mapper;

    private final String selectByIdQuery;


    public DormitoryRepositoryImpl(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.mapper = new BeanPropertyRowMapper<>(Dormitory.class);
        this.selectByIdQuery = """
            select * from dormitories where id = :id
            """;
    }

    @Override
    public Optional<Dormitory> findById(long id) {
        MapSqlParameterSource params = new MapSqlParameterSource("id", id);
        List<Dormitory> dormitories = jdbcTemplate.query(selectByIdQuery, params, mapper);
        if (dormitories.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(dormitories.get(0));
    }
}
