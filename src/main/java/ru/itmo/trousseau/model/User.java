package ru.itmo.trousseau.model;

import jakarta.persistence.Column;
import lombok.Data;

@Data
public class User {
    private Long id;
    private String login;
    private String password;
    private Role role;
    @Column(name = "dormitory_id")
    private Long dormitoryId;
    private String room;
}
