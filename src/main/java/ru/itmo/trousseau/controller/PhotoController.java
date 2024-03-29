package ru.itmo.trousseau.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import ru.itmo.trousseau.model.Photo;
import ru.itmo.trousseau.service.PhotoService;

@Controller
@RequiredArgsConstructor
public class PhotoController {

    private final PhotoService photoService;

    @GetMapping(path = "/photos/{photo_id}", produces = MediaType.IMAGE_JPEG_VALUE)
    @ResponseBody
    public byte[] getPhoto(@PathVariable("photo_id") long photoId) {
        Photo photo = photoService.findById(photoId);
        return photo.getData();
    }
}
