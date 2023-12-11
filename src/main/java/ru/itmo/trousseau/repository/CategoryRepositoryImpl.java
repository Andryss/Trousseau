package ru.itmo.trousseau.repository;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.itmo.trousseau.model.CategoryWithGroup;

import java.util.List;

@Repository
public class CategoryRepositoryImpl implements CategoryRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final RowMapper<CategoryWithGroup> mapper;

    private final String selectAllWithGroupsQuery;
    private final String selectAllByItemIdQuery;


    public CategoryRepositoryImpl(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.mapper = new BeanPropertyRowMapper<>(CategoryWithGroup.class);
        this.selectAllWithGroupsQuery = """
            select categories.id, categories.name, categories.description,
                category_groups.name as group_name, category_groups.description as group_description
            from categories join category_groups on categories.group_id = category_groups.id
            """;
        this.selectAllByItemIdQuery = """
            select categories.id, categories.name, categories.description,
                category_groups.name as group_name, category_groups.description as group_description
            from item_categories join categories on item_categories.category_id = categories.id
                join category_groups on categories.group_id = category_groups.id
            where item_categories.item_id = :item_id
            """;
    }

    @Override
    public List<CategoryWithGroup> findAll() {
        return jdbcTemplate.query(selectAllWithGroupsQuery, mapper);
    }

    @Override
    public List<CategoryWithGroup> findAllByItemId(long id) {
        MapSqlParameterSource params = new MapSqlParameterSource("item_id", id);
        return jdbcTemplate.query(selectAllByItemIdQuery, params, mapper);
    }
}
