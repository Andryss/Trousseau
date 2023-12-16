package ru.itmo.trousseau.model;

import java.sql.Timestamp;

import jakarta.persistence.Column;
import lombok.Data;

@Data
public class Subscription {
    private Long id;
    @Column(name = "user_id")
    private Long userId;
    private String name;
    @Column(name = "creation_datetime")
    private Timestamp creationDatetime;
}
