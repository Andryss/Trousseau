package ru.itmo.trousseau.model;

import java.sql.Timestamp;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Item {
    private Long id;
    private String title;
    @Column(name = "photo_id")
    private Long photoId;
    private String description;
    private Status status;
    @Column(name = "user_id")
    private Long userId;
    @Column(name = "creation_datetime")
    private Timestamp creationDatetime;
}
