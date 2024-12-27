package ru.itmo.trousseau.service;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.itmo.trousseau.model.CategoryWithGroup;
import ru.itmo.trousseau.object.CategoryGroupDescription;

import static org.assertj.core.api.Assertions.assertThat;

class CategoryServiceTest extends BaseDbTest {

    @Autowired
    private CategoryService categoryService;

    @Test
    public void findAll_itemsExists_returnList() {
        List<CategoryGroupDescription> categories = categoryService.findAll();

        assertThat(categories).hasSize(6);

        CategoryGroupDescription category = categories.get(5);

        assertThat(category.getName()).isEqualTo("Другое");
        assertThat(category.getDescription()).isEqualTo("Включает в себя все, что не подошло под другие группы категорий");
        assertThat(category.getCategories()).hasSize(1);
    }

    @Test
    public void findAllByItemId_itemsExists_returnList() {
        List<CategoryWithGroup> categories = categoryService.findAllByItemId(1);

        assertThat(categories).hasSize(1);

        CategoryWithGroup category = categories.get(0);

        assertThat(category.getGroupName()).isEqualTo("Техника");
        assertThat(category.getGroupDescription()).isEqualTo("Включает в себя различные устройства и оборудование, которые используются в повседневной жизни для выполнения различных задач и упрощения процессов");
        assertThat(category.getId()).isEqualTo(5);
        assertThat(category.getName()).isEqualTo("Провод");
        assertThat(category.getDescription()).isEqualTo("Электротехническое изделие, служащее для соединения источника электрического тока с потребителем, компонентами электрической схемы.");
    }

    @Test
    public void findAllByItemId_itemsAbsent_returnEmptyList() {
        List<CategoryWithGroup> categories = categoryService.findAllByItemId(-1);

        assertThat(categories).isEmpty();
    }
}