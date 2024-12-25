package ru.itmo.trousseau.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Role {
    ADMIN("ADMIN"),
    USER("USER");

    private final String name;
}
