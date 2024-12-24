package ru.itmo.trousseau.security;

import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.itmo.trousseau.model.User;
import ru.itmo.trousseau.repository.PrivilegesRepository;
import ru.itmo.trousseau.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PrivilegesRepository privilegesRepository;

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        User user = userRepository.findByLoginIgnoreCase(login).orElseThrow(() -> new UsernameNotFoundException(login));
        List<String> privileges = privilegesRepository.findAllForUser(user.getId());
        return new CustomUserDetails(
                user.getLogin(),
                user.getPassword(),
                privileges.stream().map(SimpleGrantedAuthority::new).toList()
        );
    }
}
