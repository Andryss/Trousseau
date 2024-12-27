package ru.itmo.trousseau.service;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.itmo.trousseau.exception.ForbiddenException;
import ru.itmo.trousseau.exception.NotFoundException;
import ru.itmo.trousseau.model.Item;
import ru.itmo.trousseau.model.Status;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class BookingServiceTest extends BaseDbTest {

    @Autowired
    private BookingService bookingService;

    @Test
    public void findAllBookedBy_userAbsent_returnEmptyList() {
        List<Item> booked = bookingService.findAllBookedBy(-1);

        assertThat(booked).isEmpty();
    }

    @Test
    public void findAllBookedBy_hasSomeBooked_returnBooked() {
        List<Item> booked = bookingService.findAllBookedBy(4);

        assertThat(booked).hasSize(1);

        Item item = booked.get(0);

        assertThat(item.getId()).isEqualTo(3);
        assertThat(item.getStatus()).isEqualTo(Status.BLOCKED);
        assertThat(item.getTitle()).isEqualTo("Набор шампуней и гелей для душа");
    }

    @Test
    public void bookItem_userHasNoBookings_itemBooked() {
        List<Item> bookedBefore = bookingService.findAllBookedBy(1);

        assertThat(bookedBefore).isEmpty();

        bookingService.bookItem(1, "admin");

        List<Item> bookedAfter = bookingService.findAllBookedBy(1);

        assertThat(bookedAfter).hasSize(1);

        Item item = bookedAfter.get(0);

        assertThat(item.getId()).isEqualTo(1);
    }

    @Test
    public void bookItem_userAbsent_throwException() {
        assertThatThrownBy(() -> bookingService.bookItem(1, "absent"))
                .isInstanceOf(NotFoundException.class);
    }

    @Test
    public void bookItem_itemAbsent_throwException() {
        assertThatThrownBy(() -> bookingService.bookItem(-1, "user1"))
                .isInstanceOf(NotFoundException.class);
    }

    @Test
    public void bookItem_userIsOwner_throwException() {
        assertThatThrownBy(() -> bookingService.bookItem(1, "user1"))
                .isInstanceOf(ForbiddenException.class);
    }
}