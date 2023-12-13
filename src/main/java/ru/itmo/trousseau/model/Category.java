package ru.itmo.trousseau.model;

import jakarta.persistence.Column;
import lombok.Data;

@Data
public class Category {
    private Long id;
    private String name;
    private String description;
    @Column(name = "group_id")
    private Long groupId;
}
