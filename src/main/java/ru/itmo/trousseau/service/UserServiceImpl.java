package ru.itmo.trousseau.service;

import java.util.Optional;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.itmo.trousseau.exception.NotFoundException;
import ru.itmo.trousseau.model.User;
import ru.itmo.trousseau.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public User findByUsername(String username) {
        Optional<User> userOptional = userRepository.findByLoginIgnoreCase(username);
        return userOptional.orElseThrow(() -> new NotFoundException(username));
    }
}
