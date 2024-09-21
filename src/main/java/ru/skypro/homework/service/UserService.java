package ru.skypro.homework.service;

import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.NewPassword;
import ru.skypro.homework.dto.UpdateUser;
import ru.skypro.homework.dto.User;

/**
 * Интерфейс для работы с пользователями.
 */
public interface UserService {

    /**
     * Устанавливает новый пароль для текущего пользователя.
     *
     * @param newPassword Данные для смены пароля.
     */
    void setPassword(NewPassword newPassword);

    /**
     * Получает информацию о текущем пользователе.
     *
     * @return Информация о текущем пользователе.
     */
    User getCurrentUser();

    /**
     * Обновляет информацию о текущем пользователе.
     *
     * @param updateUser Данные для обновления информации о пользователе.
     * @return Обновленная информация о пользователе.
     */
    UpdateUser updateUser(UpdateUser updateUser);

    /**
     * Обновляет аватар текущего пользователя.
     *
     * @param image Новое изображение для аватара.
     */
    void updateUserAvatar(MultipartFile image);

    /**
     * Проверяет, имеет ли пользователь доступ к смене пароля.
     *
     * @param username Имя пользователя.
     * @return true, если пользователь имеет доступ, иначе false.
     */
    boolean hasAccessToChangePassword(String username);
}