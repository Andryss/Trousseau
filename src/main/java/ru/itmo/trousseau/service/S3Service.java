package ru.itmo.trousseau.service;

import org.springframework.web.multipart.MultipartFile;

public interface S3Service {
    void put(String path, MultipartFile file);
    byte[] get(String path);
}
