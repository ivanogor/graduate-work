package ru.skypro.homework.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.skypro.homework.entity.AuthorityEntity;

/**
 * Репозиторий для работы с сущностями прав доступа пользователей.
 */
public interface AuthorityRepository extends JpaRepository<AuthorityEntity, String> {
}