package ru.itmo.trousseau.service;

import java.time.Instant;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.itmo.trousseau.exception.NotFoundException;
import ru.itmo.trousseau.model.Photo;
import ru.itmo.trousseau.repository.PhotoRepository;

@Slf4j
@Service
@RequiredArgsConstructor
public class PhotoServiceImpl implements PhotoService {

    private final PhotoRepository photoRepository;
    private final S3Service s3Service;
    private final TimeService timeService;

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmssSSS");

    @Override
    public Photo findById(long id) {
        Optional<Photo> photoOptional = photoRepository.findById(id);
        return photoOptional.orElseThrow(() -> new NotFoundException(String.valueOf(id)));
    }

    @Override
    public byte[] findById(long id, String author) {
        try {
            log.error("Trying to read photo data from S3");
            ZonedDateTime instant = Instant.ofEpochMilli(id).atZone(ZoneOffset.UTC);
            String path = String.format("%s/%s", author, formatter.format(instant));
            return s3Service.get(path);
        } catch (Exception e) {
            log.error("Error while reading photo data from S3. Trying to read from DB", e);
            Photo photo = photoRepository.findById(id).orElseThrow(() -> new NotFoundException(String.valueOf(id)));
            return photo.getData();
        }
    }

    @Override
    public String save(MultipartFile file, String author) {
        ZonedDateTime now = timeService.now().atZone(ZoneOffset.UTC);
        String path = String.format("%s/%s", author, formatter.format(now));
        s3Service.put(path, file);
        return path;
    }

    @Override
    public long saveOld(MultipartFile file, String author) {
        ZonedDateTime now = timeService.now().atZone(ZoneOffset.UTC);
        String path = String.format("%s/%s", author, formatter.format(now));
        s3Service.put(path, file);
        return now.toInstant().toEpochMilli();
    }
}
