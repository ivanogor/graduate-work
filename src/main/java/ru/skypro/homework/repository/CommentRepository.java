package ru.skypro.homework.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.skypro.homework.entity.CommentEntity;

import java.util.ArrayList;

/**
 * Репозиторий для работы с сущностями комментариев.
 */
public interface CommentRepository extends JpaRepository<CommentEntity, Integer> {

    /**
     * Находит все комментарии, относящиеся к указанному объявлению.
     *
     * @param ad ID объявления.
     * @return Список комментариев, относящихся к указанному объявлению.
     */
    ArrayList<CommentEntity> findAllByAd(Long ad);
}