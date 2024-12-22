package ru.itmo.trousseau.service;

import org.springframework.web.multipart.MultipartFile;
import ru.itmo.trousseau.model.Photo;

public interface PhotoService {
    @Deprecated
    Photo findById(long id);
    byte[] findById(long id, String author);
    String save(MultipartFile file, String author);
    long saveOld(MultipartFile file, String author);
}
