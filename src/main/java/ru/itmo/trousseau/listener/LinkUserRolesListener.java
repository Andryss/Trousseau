package ru.itmo.trousseau.listener;

import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import ru.itmo.trousseau.repository.RolesRepository;

@Component
@RequiredArgsConstructor
public class LinkUserRolesListener implements ApplicationListener<ContextRefreshedEvent> {

    private final RolesRepository rolesRepository;

    @Override
    public void onApplicationEvent(@NotNull ContextRefreshedEvent event) {
        rolesRepository.findAllUsersWithoutRoles().forEach(rolesRepository::saveUserRole);
    }
}
