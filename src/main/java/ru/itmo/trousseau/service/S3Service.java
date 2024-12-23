package ru.itmo.trousseau.service;

import org.springframework.web.multipart.MultipartFile;

public interface S3Service {
    void put(String path, MultipartFile file);
    boolean exists(String path);
    byte[] get(String path);
}
