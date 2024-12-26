package ru.itmo.trousseau.service;

import java.time.Instant;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

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
    public byte[] findById(long id) {
        try {
            log.info("Trying to read photo data from S3");
            return s3Service.get(toNewId(id));
        } catch (Exception e) {
            log.error("Error while reading photo data from S3. Trying to read from DB", e);
            Photo photo = photoRepository.findById(id).orElseThrow(() -> new NotFoundException(String.valueOf(id)));
            return photo.getData();
        }
    }

    @Override
    public String save(MultipartFile file) {
        ZonedDateTime now = timeService.now().atZone(ZoneOffset.UTC);
        String path = formatter.format(now);
        s3Service.put(path, file);
        return path;
    }

    @Override
    public long saveOld(MultipartFile file) {
        ZonedDateTime now = timeService.now().atZone(ZoneOffset.UTC);
        String path = formatter.format(now);
        s3Service.put(path, file);
        return now.toInstant().toEpochMilli();
    }

    @Override
    public String toNewId(long oldId) {
        ZonedDateTime instant = Instant.ofEpochMilli(oldId).atZone(ZoneOffset.UTC);
        return formatter.format(instant);
    }
}
