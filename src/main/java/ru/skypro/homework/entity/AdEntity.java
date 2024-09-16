package ru.skypro.homework.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * Сущность для представления объявления в базе данных.
 */
@Entity
@Data
@Builder
@Table(name = "ads")
@AllArgsConstructor
@NoArgsConstructor
public class AdEntity {

    /**
     * Уникальный идентификатор объявления.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pk")
    private Integer pk;

    /**
     * ID автора объявления.
     */
    @Column(name = "author_id")
    private Long author;

    /**
     * Цена объявления.
     */
    @Column(name = "price")
    private Integer price;

    /**
     * Заголовок объявления.
     */
    @Column(name = "title")
    private String title;

    /**
     * Ссылка на изображение объявления.
     */
    @Column(name = "image")
    private String image;

    /**
     * Описание объявления.
     */
    @Column(name = "description")
    private String description;

    /**
     * Связь с сущностью пользователя.
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private UserEntity user;
}