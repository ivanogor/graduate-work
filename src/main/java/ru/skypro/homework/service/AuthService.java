package ru.skypro.homework.service;

import ru.skypro.homework.dto.Register;

/**
 * Интерфейс для аутентификации и регистрации пользователей.
 */
public interface AuthService {
    /**
     * Выполняет аутентификацию пользователя.
     *
     * @param userName Имя пользователя.
     * @param password Пароль пользователя.
     * @return true, если аутентификация прошла успешно, иначе false.
     */
    boolean login(String userName, String password);

    /**
     * Регистрирует нового пользователя.
     *
     * @param register Данные для регистрации пользователя.
     * @return true, если регистрация прошла успешно, иначе false.
     */
    boolean register(Register register);
}