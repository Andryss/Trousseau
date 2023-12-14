package ru.itmo.trousseau.repository;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.itmo.trousseau.model.Category;
import ru.itmo.trousseau.model.CategoryWithGroup;

@Repository
public class CategoryRepositoryImpl implements CategoryRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final RowMapper<CategoryWithGroup> mapper;

    private final String selectAllQuery;
    private final String insertForItemQuery;
    private final String selectAllWithGroupsQuery;
    private final String selectAllByItemIdQuery;


    public CategoryRepositoryImpl(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.mapper = new BeanPropertyRowMapper<>(CategoryWithGroup.class);
        this.selectAllQuery = """
            select * from categories
            """;
        this.insertForItemQuery = """
            insert into item_categories values (:itemId, :categoryId)
            """;
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
        return jdbcTemplate.query(selectAllWithGroupsQuery, Map.of(), mapper);
    }

    @Override
    public List<CategoryWithGroup> findAllByItemId(long id) {
        MapSqlParameterSource params = new MapSqlParameterSource("item_id", id);
        return jdbcTemplate.query(selectAllByItemIdQuery, params, mapper);
    }

    @Override
    public void saveAll(long itemId, String[] categories) {
        Map<String, Long> categoryMap = jdbcTemplate.queryForList(selectAllQuery, Map.of(), Category.class).stream()
                .collect(Collectors.toMap(Category::getName, Category::getId));
        for (String category : categories) {
            Long categoryId = categoryMap.get(category);
            MapSqlParameterSource params = new MapSqlParameterSource();
            params.addValue("itemId", itemId);
            params.addValue("categoryId", categoryId);
            jdbcTemplate.update(insertForItemQuery, params);
        }
    }
}
