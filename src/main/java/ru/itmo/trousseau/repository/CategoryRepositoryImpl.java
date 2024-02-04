package ru.itmo.trousseau.repository;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.itmo.trousseau.model.Category;
import ru.itmo.trousseau.model.CategoryWithGroup;

@Repository
@RequiredArgsConstructor
public class CategoryRepositoryImpl implements CategoryRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final RowMapper<Category> mapper = new BeanPropertyRowMapper<>(Category.class);
    private final RowMapper<CategoryWithGroup> mapperWithGroup = new BeanPropertyRowMapper<>(CategoryWithGroup.class);

    @Override
    public List<CategoryWithGroup> findAll() {
        return jdbcTemplate.query("""
            select categories.id, categories.name, categories.description, category_groups.id as group_id,
                category_groups.name as group_name, category_groups.description as group_description
            from categories join category_groups on categories.group_id = category_groups.id
            """, Map.of(), mapperWithGroup);
    }

    @Override
    public List<CategoryWithGroup> findAllByItemId(long itemId) {
        MapSqlParameterSource params = new MapSqlParameterSource("item_id", itemId);
        return jdbcTemplate.query("""
            select categories.id, categories.name, categories.description,
                category_groups.name as group_name, category_groups.description as group_description
            from item_categories join categories on item_categories.category_id = categories.id
                join category_groups on categories.group_id = category_groups.id
            where item_categories.item_id = :item_id
            """, params, mapperWithGroup);
    }

    @Override
    public List<CategoryWithGroup> findAllBySubscriptionId(long subId) {
        MapSqlParameterSource params = new MapSqlParameterSource("subId", subId);
        return jdbcTemplate.query("""
            select categories.id, categories.name, categories.description,
                category_groups.name as group_name, category_groups.description as group_description
            from subscription_categories join categories on subscription_categories.category_id = categories.id
                join category_groups on categories.group_id = category_groups.id
            where subscription_categories.subscription_id = :subId
            """, params, mapperWithGroup);
    }

    @Override
    public void saveAllForItem(long itemId, String[] categories) {
        Map<String, Long> categoryMap = jdbcTemplate.query("""
            select * from categories
            """, Map.of(), mapper).stream()
                .collect(Collectors.toMap(Category::getName, Category::getId));
        for (String category : categories) {
            Long categoryId = categoryMap.get(category);
            MapSqlParameterSource params = new MapSqlParameterSource();
            params.addValue("itemId", itemId);
            params.addValue("categoryId", categoryId);
            jdbcTemplate.update("""
                insert into item_categories values (:itemId, :categoryId)
                """, params);
        }
    }

    @Override
    public void saveAllForSubscription(long subId, String[] categories) {
        Map<String, Long> categoryMap = jdbcTemplate.query("""
            select * from categories
            """, Map.of(), mapper).stream()
                .collect(Collectors.toMap(Category::getName, Category::getId));
        for (String category : categories) {
            Long categoryId = categoryMap.get(category);
            MapSqlParameterSource params = new MapSqlParameterSource();
            params.addValue("subId", subId);
            params.addValue("categoryId", categoryId);
            jdbcTemplate.update("""
                insert into subscription_categories values (:subId, :categoryId)
                """, params);
        }
    }
}
