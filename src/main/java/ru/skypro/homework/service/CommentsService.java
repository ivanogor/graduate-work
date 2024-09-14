package ru.skypro.homework.service;

import org.springframework.security.core.Authentication;
import ru.skypro.homework.dto.Comment;
import ru.skypro.homework.dto.Comments;
import ru.skypro.homework.dto.CreateOrUpdateComment;

public interface CommentsService {
    Comment createComment(Long ad,CreateOrUpdateComment createComment, Authentication authentication);
    Comments getAll(Long ad);
    void deleteComment(Integer id, Authentication authentication);
    Comment updateComment(Integer id,CreateOrUpdateComment comment, Authentication authentication);
}
