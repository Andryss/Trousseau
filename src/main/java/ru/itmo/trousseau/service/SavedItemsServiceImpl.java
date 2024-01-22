package ru.itmo.trousseau.service;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.itmo.trousseau.exception.ConflictException;
import ru.itmo.trousseau.exception.NotFoundException;
import ru.itmo.trousseau.model.Item;
import ru.itmo.trousseau.model.User;
import ru.itmo.trousseau.repository.SavedItemsRepository;
import ru.itmo.trousseau.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class SavedItemsServiceImpl implements SavedItemsService {

    private final UserRepository userRepository;
    private final SavedItemsRepository savedItemsRepository;

    @Override
    public List<Item> findAllSavedBy(long userId) {
        return savedItemsRepository.findAllByUserId(userId);
    }

    @Override
    public void addToSaved(String username, long itemId) {
        User user = userRepository.findByLoginIgnoreCase(username).orElseThrow(() -> new NotFoundException(username));

        List<Long> alreadySaved = savedItemsRepository.findIdsByUserId(user.getId());
        if (alreadySaved.contains(itemId)) {
            return;
        }

        savedItemsRepository.save(user.getId(), itemId, Timestamp.from(Instant.now()));
    }

    @Override
    public void deleteFromSaved(String username, long itemId) {
        User user = userRepository.findByLoginIgnoreCase(username).orElseThrow(() -> new NotFoundException(username));

        List<Long> alreadySaved = savedItemsRepository.findIdsByUserId(user.getId());
        if (!alreadySaved.contains(itemId)) {
            throw new ConflictException("not saved");
        }

        savedItemsRepository.delete(user.getId(), itemId);
    }
}
