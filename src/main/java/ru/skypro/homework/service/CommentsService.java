package ru.skypro.homework.service;

import org.springframework.security.core.Authentication;
import ru.skypro.homework.dto.Comment;
import ru.skypro.homework.dto.Comments;
import ru.skypro.homework.dto.CreateOrUpdateComment;

/**
 * Интерфейс для работы с комментариями.
 */
public interface CommentsService {

    /**
     * Создает новый комментарий к указанному объявлению.
     *
     * @param ad ID объявления.
     * @param createComment Данные для создания комментария.
     * @param authentication Аутентификация пользователя.
     * @return Созданный комментарий.
     */
    Comment createComment(Long ad, CreateOrUpdateComment createComment, Authentication authentication);

    /**
     * Получает все комментарии к указанному объявлению.
     *
     * @param ad ID объявления.
     * @return Список всех комментариев к указанному объявлению.
     */
    Comments getAll(Long ad);

    /**
     * Удаляет комментарий по его ID.
     *
     * @param id ID комментария.
     * @param authentication Аутентификация пользователя.
     */
    void deleteComment(Integer id, Authentication authentication);

    /**
     * Обновляет информацию о комментарии по его ID.
     *
     * @param id ID комментария.
     * @param comment Данные для обновления комментария.
     * @param authentication Аутентификация пользователя.
     * @return Обновленный комментарий.
     */
    Comment updateComment(Integer id, CreateOrUpdateComment comment, Authentication authentication);
}