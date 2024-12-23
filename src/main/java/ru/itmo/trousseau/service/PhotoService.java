package ru.itmo.trousseau.service;

import org.springframework.web.multipart.MultipartFile;

public interface PhotoService {
    byte[] findById(long id);
    String save(MultipartFile file);
    long saveOld(MultipartFile file);
}
