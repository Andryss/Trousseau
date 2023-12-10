package ru.itmo.trousseau.model;

import java.time.Instant;

import jakarta.persistence.Column;
import lombok.Data;

@Data
public class Item {
    private long id;
    private String title;
    @Column(name = "photo_id")
    private long photoId;
    private String description;
    private Status status;
    @Column(name = "user_id")
    private long userId;
    @Column(name = "creation_datetime")
    private Instant creationDatetime;
}
