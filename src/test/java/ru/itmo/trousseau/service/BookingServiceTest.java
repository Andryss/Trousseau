package ru.itmo.trousseau.service;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.itmo.trousseau.model.Item;
import ru.itmo.trousseau.model.Status;

import static org.assertj.core.api.Assertions.assertThat;

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
}