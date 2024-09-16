package ru.skypro.homework.utils;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import ru.skypro.homework.entity.UserEntity;
import ru.skypro.homework.repository.UserEntityRepository;

/**
 * Утилитный сервис для работы с пользователями в контексте объявлений.
 */
@Service
@RequiredArgsConstructor
public class AdServiceUtils {
    private final Logger logger = LoggerFactory.getLogger(AdServiceUtils.class);

    private final UserEntityRepository userRepository;

    /**
     * Обрабатывает аутентификационные данные пользователя и возвращает соответствующую сущность пользователя.
     *
     * @param authentication Аутентификационные данные пользователя.
     * @return Сущность пользователя.
     */
    public UserEntity handleUser(Authentication authentication) {
        logger.info("Was invoked handle User method");
        UserDetails principal = (UserDetails) authentication.getPrincipal();
        return userRepository.findByUsername(principal.getUsername());
    }
}