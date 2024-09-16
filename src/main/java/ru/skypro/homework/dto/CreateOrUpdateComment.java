package ru.skypro.homework.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.skypro.homework.entity.CommentEntity;

/**
 * DTO для создания или обновления комментария.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateOrUpdateComment {

    @Schema(description = "текст комментария", required = true, minLength = 8, maxLength = 64)
    private String text;

    /**
     * Преобразует DTO в сущность комментария.
     *
     * @param author   Идентификатор автора комментария.
     * @param ad       Идентификатор объявления, к которому относится комментарий.
     * @param createdAt Дата и время создания комментария.
     * @return Сущность комментария.
     */
    public CommentEntity mapDtoToEntity(Long author, Long ad, Long createdAt) {
        return CommentEntity.builder()
                .author(author)
                .createdAt(createdAt)
                .text(text)
                .ad(ad)
                .build();
    }
}