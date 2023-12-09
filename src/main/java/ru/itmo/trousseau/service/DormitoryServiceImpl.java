package ru.itmo.trousseau.service;

import java.util.Optional;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.itmo.trousseau.exception.NotFoundException;
import ru.itmo.trousseau.model.Dormitory;
import ru.itmo.trousseau.repository.DormitoryRepository;

@Service
@RequiredArgsConstructor
public class DormitoryServiceImpl implements DormitoryService {

    private final DormitoryRepository dormitoryRepository;

    @Override
    public Dormitory findById(long id) {
        Optional<Dormitory> dormitoryOptional = dormitoryRepository.findById(id);
        return dormitoryOptional.orElseThrow(() -> new NotFoundException(String.valueOf(id)));
    }
}
