package ru.skypro.homework.service.impl;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.*;
import ru.skypro.homework.entity.CommentEntity;
import ru.skypro.homework.entity.UserEntity;
import ru.skypro.homework.repository.CommentRepository;
import ru.skypro.homework.repository.UserEntityRepository;
import ru.skypro.homework.service.CommentsService;
import ru.skypro.homework.utils.AdServiceUtils;

import java.util.ArrayList;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class CommentsServiceImpl implements CommentsService {

    private final CommentRepository repository;
    private final UserEntityRepository userRepository;
    private final AdServiceUtils adUtils;
    private final Logger logger = LoggerFactory.getLogger(CommentsServiceImpl.class);


    public Comment createComment(Long ad,CreateOrUpdateComment createComment, Authentication authentication){
        logger.info("Was invoked create Comment method");
        UserEntity user = adUtils.handleUser(authentication);
        CommentEntity comment = createComment.mapDtoToEntity(user.getId(), ad,System.currentTimeMillis());
        repository.save(comment);
        return Comment.toDto(comment,user);
    }

    public Comments getAll(Long ad){
        logger.info("Was invoked getAll Comment method");
        ArrayList<Comment> comments = repository.findAllByAd(ad).stream()
                .map((CommentEntity commentEntity) -> Comment.toDto(commentEntity,userRepository.findById(commentEntity.getAuthor()).orElseThrow()))
                .collect(Collectors.toCollection(ArrayList::new));
        return new Comments().getComments(comments.size(),comments);
    }

    public void deleteComment(Integer id, Authentication authentication) {
        logger.info("Was invoked delete Comment method");
        repository.deleteById(id);
    }

    public Comment updateComment(Integer id,CreateOrUpdateComment comment, Authentication authentication) {
        logger.info("Was invoked update Ad method");
        CommentEntity commentEnt = repository.findById(id).orElseThrow();
        UserEntity user = adUtils.handleUser(authentication);
        commentEnt.setText(comment.getText());
        repository.save(commentEnt);
        return Comment.toDto(commentEnt,user);
    }

    public Boolean foundById(Integer id) {
        logger.info("Was invoked find Comment by id method");
        return !repository.findById(id).isEmpty();
    }

    public CommentEntity findById(Integer id) {
        return repository.findById(id).get();
    }
}
