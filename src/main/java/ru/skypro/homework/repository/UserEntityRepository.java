package ru.skypro.homework.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.skypro.homework.entity.UserEntity;

/**
 * Репозиторий для работы с сущностями пользователей.
 */
public interface UserEntityRepository extends JpaRepository<UserEntity, Long> {

    /**
     * Находит пользователя по его логину.
     *
     * @param username Логин пользователя.
     * @return Сущность пользователя, если найдена, иначе null.
     */
    UserEntity findByUsername(String username);

    /**
     * Проверяет, существует ли пользователь с указанным логином.
     *
     * @param username Логин пользователя.
     * @return true, если пользователь существует, иначе false.
     */
    boolean existsByUsername(String username);
}