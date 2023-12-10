package ru.itmo.trousseau.model;

import java.time.Instant;

import jakarta.persistence.Column;
import lombok.Data;

@Data
public class Photo {
    private long id;
    private byte[] data;
    @Column(name = "upload_datetime")
    private Instant uploadDatetime;
}
