package ru.itmo.trousseau.listener;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;

import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.multipart.MultipartFile;
import ru.itmo.trousseau.model.Photo;
import ru.itmo.trousseau.repository.PhotoRepository;
import ru.itmo.trousseau.service.PhotoService;
import ru.itmo.trousseau.service.S3Service;

@Component
@RequiredArgsConstructor
public class UploadPhotosToS3Listener implements ApplicationListener<ContextRefreshedEvent> {

    private final PhotoRepository photoRepository;
    private final PhotoService photoService;
    private final S3Service s3Service;

    @Override
    public void onApplicationEvent(@NotNull ContextRefreshedEvent event) {
        for (Long id : photoRepository.findAllIds()) {
            String newId = photoService.toNewId(id);
            if (!s3Service.exists(newId)) {
                Photo photo = photoRepository.findById(id).orElseThrow();
                s3Service.put(newId, new PhotoMultipartFile(photo));
            }
        }
    }

    @RequiredArgsConstructor
    static class PhotoMultipartFile implements MultipartFile {

        private final Photo photo;

        @NotNull
        @Override
        public String getName() {
            return ""; // ignore
        }

        @Override
        public String getOriginalFilename() {
            return null; // ignore
        }

        @Override
        public String getContentType() {
            return MimeTypeUtils.IMAGE_JPEG_VALUE;
        }

        @Override
        public boolean isEmpty() {
            return getSize() == 0;
        }

        @Override
        public long getSize() {
            return getBytes().length;
        }

        @NotNull
        @Override
        public byte[] getBytes() {
            return photo.getData();
        }

        @NotNull
        @Override
        public InputStream getInputStream() {
            return new ByteArrayInputStream(getBytes());
        }

        @Override
        public void transferTo(@NotNull File dest) throws IllegalStateException {
            // ignore
        }
    }
}
