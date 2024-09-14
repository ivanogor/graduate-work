package ru.skypro.homework.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.skypro.homework.entity.CommentEntity;

import java.util.ArrayList;

public interface CommentRepository extends JpaRepository<CommentEntity, Integer> {
    ArrayList<CommentEntity> findAllByAd(Long ad);
}
