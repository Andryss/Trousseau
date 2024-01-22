package ru.itmo.trousseau.service;

import java.util.Optional;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.itmo.trousseau.exception.NotFoundException;
import ru.itmo.trousseau.model.Photo;
import ru.itmo.trousseau.repository.PhotoRepository;

@Service
@RequiredArgsConstructor
public class PhotoServiceImpl implements PhotoService {

    private final PhotoRepository photoRepository;

    @Override
    public Photo findById(long id) {
        Optional<Photo> photoOptional = photoRepository.findById(id);
        return photoOptional.orElseThrow(() -> new NotFoundException(String.valueOf(id)));
    }
}
