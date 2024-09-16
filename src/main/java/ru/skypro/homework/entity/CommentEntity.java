package ru.skypro.homework.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * Сущность для представления комментария в базе данных.
 */
@Entity
@Data
@Builder
@Table(name = "comment")
@AllArgsConstructor
@NoArgsConstructor
public class CommentEntity {

    /**
     * Уникальный идентификатор комментария.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Integer pk;

    /**
     * ID автора комментария.
     */
    @Column(name = "author")
    private Long author;

    /**
     * Временная метка создания комментария.
     */
    @Column(name = "created_at")
    private Long createdAt;

    /**
     * Текст комментария.
     */
    @Column(name = "text")
    private String text;

    /**
     * ID объявления, к которому относится комментарий.
     */
    @Column(name = "ad")
    private Long ad;
}