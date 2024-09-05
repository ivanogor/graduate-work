package ru.skypro.homework.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.skypro.homework.entity.AdEntity;

import java.util.ArrayList;

public interface AdRepository extends JpaRepository<AdEntity, Integer> {
    ArrayList<AdEntity> findAllByAuthor(Long author);
}
