package ru.itmo.trousseau.model;

import java.sql.Timestamp;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Photo {
    private Long id;
    private byte[] data;
    @Column(name = "upload_datetime")
    private Timestamp uploadDatetime;
}
