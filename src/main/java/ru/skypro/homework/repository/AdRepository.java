package ru.skypro.homework.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.skypro.homework.entity.AdEntity;

import java.util.ArrayList;

/**
 * Репозиторий для работы с сущностями объявлений.
 */
public interface AdRepository extends JpaRepository<AdEntity, Integer> {

    /**
     * Находит все объявления, созданные указанным автором.
     *
     * @param author ID автора объявлений.
     * @return Список объявлений, созданных указанным автором.
     */
    ArrayList<AdEntity> findAllByAuthor(Long author);
}