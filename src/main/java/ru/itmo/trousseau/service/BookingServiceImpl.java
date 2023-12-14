package ru.itmo.trousseau.service;

import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.itmo.trousseau.exception.ConflictException;
import ru.itmo.trousseau.exception.ForbiddenException;
import ru.itmo.trousseau.exception.NotFoundException;
import ru.itmo.trousseau.model.Item;
import ru.itmo.trousseau.model.Status;
import ru.itmo.trousseau.model.User;
import ru.itmo.trousseau.repository.BookingRepository;
import ru.itmo.trousseau.repository.ItemRepository;
import ru.itmo.trousseau.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {

    private final UserRepository userRepository;
    private final ItemRepository itemRepository;
    private final BookingRepository bookingRepository;

    @Override
    public List<Item> findAllBookedBy(long userId) {
        return bookingRepository.findAllByUserId(userId);
    }

    @Override
    public void bookItem(long itemId, String username) {
        User user = userRepository.findByLoginIgnoreCase(username).orElseThrow(() -> new NotFoundException(username));
        Item item = itemRepository.findById(itemId).orElseThrow(() -> new NotFoundException(String.valueOf(itemId)));

        if (item.getUserId().equals(user.getId())) {
            throw new ForbiddenException("owner");
        }
        if (bookingRepository.countByUserId(user.getId()) >= 3) {
            throw new ConflictException("already 3");
        }
        if (item.getStatus() != Status.ACTIVE) {
            throw new ConflictException(item.getStatus().name());
        }

        bookingRepository.bookItem(itemId, user.getId());
    }

    @Override
    public void closeItem(long itemId, String username) {
        User user = userRepository.findByLoginIgnoreCase(username).orElseThrow(() -> new NotFoundException(username));
        Item item = itemRepository.findById(itemId).orElseThrow(() -> new NotFoundException(String.valueOf(itemId)));

        if (!item.getUserId().equals(user.getId())) {
            throw new ForbiddenException("not owner");
        }
        if (item.getStatus() != Status.BLOCKED) {
            throw new ConflictException(item.getStatus().name());
        }

        bookingRepository.closeItem(itemId, user.getId());
    }

    @Override
    public void cancelBooking(long itemId, String username) {
        Item item = itemRepository.findById(itemId).orElseThrow(() -> new NotFoundException(String.valueOf(itemId)));
        User user = userRepository.findByLoginIgnoreCase(username).orElseThrow(() -> new NotFoundException(username));
        long bookingOwner = bookingRepository.findUserIdByBookedItem(itemId);

        if (!user.getId().equals(item.getUserId()) && !user.getId().equals(bookingOwner)) {
            throw new ForbiddenException("not booker");
        }
        if (item.getStatus() != Status.BLOCKED) {
            throw new ConflictException(item.getStatus().name());
        }

        bookingRepository.cancelBooking(itemId, user.getId());
    }
}
