package ru.skypro.homework.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.skypro.homework.entity.CommentEntity;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateOrUpdateComment {

    @Schema(description = "текст комментария", required = true,minLength = 8,maxLength = 64)
    private String text;

    public CommentEntity mapDtoToEntity(Long author,Long ad,Long cratedAt){
        return CommentEntity.builder()
                .author(author)
                .createdAt(cratedAt)
                .text(text)
                .ad(ad)
                .build();
    }
}
