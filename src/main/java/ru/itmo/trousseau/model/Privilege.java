package ru.itmo.trousseau.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Privilege {
    SUPERUSER("SUPERUSER");

    private final String name;
}
