package ru.skypro.homework.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.skypro.homework.entity.UserEntity;
import ru.skypro.homework.repository.UserEntityRepository;

import java.util.Objects;

/**
 * Сервис для загрузки пользовательских данных в контекст Spring Security.
 */
@Service
@RequiredArgsConstructor
public class SpecialUserDetailsService implements UserDetailsService {
    private final UserEntityRepository userEntityRepository;

    /**
     * Загружает пользователя по его имени (логину).
     *
     * @param username Имя пользователя (логин).
     * @return Объект UserDetails, представляющий пользователя.
     * @throws UsernameNotFoundException Если пользователь с указанным именем не найден.
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Находим пользователя в БД по email
        UserEntity user = userEntityRepository.findByUsername(username);
        if (Objects.isNull(user)) {
            throw new UsernameNotFoundException("Username not found: " + username);
        }
        // Загружаем найденного пользователя в контекст Spring Security и возвращаем его
        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getEmail())
                .password(user.getPassword())
                .roles(user.getRole().name())
                .build();
    }
}