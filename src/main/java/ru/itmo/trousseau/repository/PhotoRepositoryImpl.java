package ru.itmo.trousseau.repository;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.itmo.trousseau.model.Photo;

@Repository
@RequiredArgsConstructor
public class PhotoRepositoryImpl implements PhotoRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final RowMapper<Photo> mapper = new BeanPropertyRowMapper<>(Photo.class);

    @Override
    public long save(byte[] data, Timestamp uploadDatetime) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("data", data);
        params.addValue("uploadDatetime", uploadDatetime);
        //noinspection DataFlowIssue,ConstantConditions
        return jdbcTemplate.queryForObject("""
            select * from insert_photo(:data, :uploadDatetime)
            """, params, Long.class);
    }

    @Override
    public Optional<Photo> findById(long id) {
        MapSqlParameterSource params = new MapSqlParameterSource("id", id);
        List<Photo> dormitories = jdbcTemplate.query("""
            select * from photos where id = :id
            """, params, mapper);
        if (dormitories.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(dormitories.get(0));
    }
}
