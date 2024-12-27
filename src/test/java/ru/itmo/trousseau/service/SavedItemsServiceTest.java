package ru.itmo.trousseau.service;

import java.sql.Timestamp;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.itmo.trousseau.exception.ConflictException;
import ru.itmo.trousseau.exception.NotFoundException;
import ru.itmo.trousseau.model.Item;
import ru.itmo.trousseau.model.Status;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class SavedItemsServiceTest extends BaseDbTest{

    @Autowired
    private SavedItemsService savedItemsService;

    @Test
    public void findAllSavedBy_userHasNoSaved_returnEmptyList() {
        List<Item> saved = savedItemsService.findAllSavedBy(1);

        assertThat(saved).isEmpty();
    }

    @Test
    public void findAllSavedBy_userAbsent_returnEmptyList() {
        List<Item> saved = savedItemsService.findAllSavedBy(-1);

        assertThat(saved).isEmpty();
    }

    @Test
    public void findAllSavedBy_userHasSomeSaved_returnEmptyList() {
        List<Item> saved = savedItemsService.findAllSavedBy(4);

        assertThat(saved).hasSize(3);

        Item item = saved.get(2);

        assertThat(item.getTitle()).isEqualTo("Трусы с волком");
        assertThat(item.getPhotoId()).isEqualTo(5);
        assertThat(item.getDescription()).isEqualTo("Мужские боксеры созданы из 100% хлопок привезенного из США, с внутренней подкладкой для комфорта, Ручная работа , размеры M,L");
        assertThat(item.getStatus()).isEqualTo(Status.PUBLISHED);
        assertThat(item.getUserId()).isEqualTo(3);
        assertThat(item.getCreationDatetime()).isEqualTo(Timestamp.valueOf("2023-09-03 16:20:00"));
    }

    @Test
    public void addToSaved_userHasNoSaved_addToSaved() {
        List<Item> savedBefore = savedItemsService.findAllSavedBy(1);

        assertThat(savedBefore).isEmpty();

        savedItemsService.addToSaved("admin", 1);

        List<Item> savedAfter = savedItemsService.findAllSavedBy(1);

        assertThat(savedAfter).hasSize(1);
    }

    @Test
    public void addToSaved_alreadySaved_noChange() {
        List<Item> savedBefore = savedItemsService.findAllSavedBy(3);

        assertThat(savedBefore).hasSize(1);

        savedItemsService.addToSaved("user2", 4);

        List<Item> savedAfter = savedItemsService.findAllSavedBy(3);

        assertThat(savedAfter).hasSize(1);
    }

    @Test
    public void addToSaved_userAbsent_throwException() {
        assertThatThrownBy(() -> savedItemsService.addToSaved("absent", 1))
                .isInstanceOf(NotFoundException.class);
    }

    @Test
    public void deleteFromSaved_userHasNoSaved_throwException() {
        assertThatThrownBy(() -> savedItemsService.deleteFromSaved("admin", 2))
                .isInstanceOf(ConflictException.class);
    }

    @Test
    public void deleteFromSaved_hasInSaved_deleteFromSaved() {
        List<Item> savedBefore = savedItemsService.findAllSavedBy(4);

        assertThat(savedBefore).hasSize(3);

        savedItemsService.deleteFromSaved("user3", 4);

        List<Item> savedAfter = savedItemsService.findAllSavedBy(4);

        assertThat(savedAfter).hasSize(2);
    }

    @Test
    public void deleteFromSaved_userAbsent_throwException() {
        assertThatThrownBy(() -> savedItemsService.deleteFromSaved("absent", 1))
                .isInstanceOf(NotFoundException.class);
    }

}