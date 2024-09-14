package ru.skypro.homework.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Comments {
    @Schema(description = "общее количество комментариев")
    private Integer count;
    private List<Comment> results;

    public Comments getComments(Integer countComments, ArrayList<Comment> comments) {
        return Comments.builder()
                .count(countComments)
                .results(comments)
                .build();
    }
}
