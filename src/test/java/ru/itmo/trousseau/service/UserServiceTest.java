package ru.itmo.trousseau.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.itmo.trousseau.exception.NotFoundException;
import ru.itmo.trousseau.model.Role;
import ru.itmo.trousseau.model.User;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class UserServiceTest extends BaseDbTest {

    @Autowired
    private UserService userService;

    @Test
    public void findById_userExists_returnUser() {
        User user = userService.findById(1);

        assertThat(user.getId()).isEqualTo(1);
        assertThat(user.getLogin()).isEqualTo("admin");
        assertThat(user.getRole()).isEqualTo(Role.ADMIN);
        assertThat(user.getDormitoryId()).isEqualTo(1);
        assertThat(user.getRoom()).isEqualTo("101");
    }

    @Test
    public void findById_userAbsent_throwException() {
        assertThatThrownBy(() -> userService.findById(-1))
                .isInstanceOf(NotFoundException.class);
    }

    @Test
    public void findByUsername_userExists_returnUser() {
        User user = userService.findByUsername("admin");

        assertThat(user.getId()).isEqualTo(1);
        assertThat(user.getLogin()).isEqualTo("admin");
        assertThat(user.getRole()).isEqualTo(Role.ADMIN);
        assertThat(user.getDormitoryId()).isEqualTo(1);
        assertThat(user.getRoom()).isEqualTo("101");
    }

    @Test
    public void findByUsername_userAbsent_throwException() {
        assertThatThrownBy(() -> userService.findByUsername("non existent"))
                .isInstanceOf(NotFoundException.class);
    }

}