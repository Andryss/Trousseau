package ru.itmo.trousseau.service;

import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import ru.itmo.trousseau.exception.NotFoundException;
import ru.itmo.trousseau.messages.SearchRequest;
import ru.itmo.trousseau.model.Item;
import ru.itmo.trousseau.model.Status;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ItemServiceTest extends BaseDbTest {

    @Autowired
    private ItemService itemService;

    @Test
    public void findById_itemExists_returnItem() {
        Item item = itemService.findById(5);

        assertThat(item.getTitle()).isEqualTo("Трусы с волком");
        assertThat(item.getPhotoId()).isEqualTo(5);
        assertThat(item.getDescription()).isEqualTo("Мужские боксеры созданы из 100% хлопок привезенного из США, с внутренней подкладкой для комфорта, Ручная работа , размеры M,L");
        assertThat(item.getStatus()).isEqualTo(Status.PUBLISHED);
        assertThat(item.getUserId()).isEqualTo(3);
        assertThat(item.getCreationDatetime()).isEqualTo(Timestamp.valueOf("2023-09-03 16:20:00"));
    }

    @Test
    public void findById_itemAbsent_throwException() {
        assertThatThrownBy(() -> itemService.findById(-1))
                .isInstanceOf(NotFoundException.class);
    }

    @Test
    public void findAllOwnedBy_userHasNoItems_returnEmptyList() {
        List<Item> items = itemService.findAllOwnedBy(1);

        assertThat(items).isEmpty();
    }

    @Test
    public void findAllOwnedBy_userHasSomeItems_returnItems() {
        List<Item> items = itemService.findAllOwnedBy(3);

        assertThat(items).hasSize(2);

        Item item = items.get(1);

        assertThat(item.getTitle()).isEqualTo("Трусы с волком");
        assertThat(item.getPhotoId()).isEqualTo(5);
        assertThat(item.getDescription()).isEqualTo("Мужские боксеры созданы из 100% хлопок привезенного из США, с внутренней подкладкой для комфорта, Ручная работа , размеры M,L");
        assertThat(item.getStatus()).isEqualTo(Status.PUBLISHED);
        assertThat(item.getUserId()).isEqualTo(3);
        assertThat(item.getCreationDatetime()).isEqualTo(Timestamp.valueOf("2023-09-03 16:20:00"));
    }

    @Test
    public void findAllOwnedBy_userAbsent_returnEmptyList() {
        List<Item> items = itemService.findAllOwnedBy(-1);

        assertThat(items).isEmpty();
    }

    @ParameterizedTest
    @MethodSource("provideSearchRequestData")
    public void findAllWithSearch_searchQuery_returnAllItems(String query, String[] categories, int expected) {
        SearchRequest search = new SearchRequest();
        search.setQuery(query);
        search.setCategories(categories);

        List<Item> items = itemService.findAllWithSearch(search);

        assertThat(items).hasSize(expected);
    }

    private static Stream<Arguments> provideSearchRequestData() {
        return Stream.of(
                Arguments.of("", new String[0], 4),
                Arguments.of("для душа", new String[0], 0),
                Arguments.of("ПрОвОд", new String[0], 1),
                Arguments.of("продам", new String[0], 1),
                Arguments.of("", new String[]{"Шапка"}, 1),
                Arguments.of("поиск", new String[]{"Перчатки", "Тарелка", "Тряпка"}, 0)
        );
    }

}