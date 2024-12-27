package ru.itmo.trousseau.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.itmo.trousseau.exception.NotFoundException;
import ru.itmo.trousseau.model.Dormitory;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class DormitoryServiceTest extends BaseDbTest {

    @Autowired
    private DormitoryService dormitoryService;

    @Test
    public void findById_dormExists_returnDorm() {
        Dormitory dormitory = dormitoryService.findById(1);

        assertThat(dormitory.getId()).isEqualTo(1);
        assertThat(dormitory.getName()).isEqualTo("Студенческий городок");
        assertThat(dormitory.getAddress()).isEqualTo("Санкт-Петербург, пер. Вяземский, д. 5/7");
    }

    @Test
    public void findById_dormAbsent_throwException() {
        assertThatThrownBy(() -> dormitoryService.findById(-1))
                .isInstanceOf(NotFoundException.class);
    }
}