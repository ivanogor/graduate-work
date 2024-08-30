package ru.skypro.homework.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.skypro.homework.dto.Comment;
import ru.skypro.homework.dto.Comments;
import ru.skypro.homework.dto.CreateOrUpdateComment;

@Slf4j
@CrossOrigin(value = "http://localhost:3000")
@RestController
@RequiredArgsConstructor
@Tag(name = "Комментарии")
@RequestMapping("ads")
public class CommentsController {

    @GetMapping("/{id}/comments")
    @Operation(summary = "Получение комментариев объявления")
    @ApiResponses(value = {@ApiResponse(responseCode = "200",
                                        description = "Successfully retrieved",
                                        content = {@Content(mediaType = "application/json",
                                                            schema = @Schema(implementation = Comments.class))}),
                            @ApiResponse(responseCode = "401", description = "Unauthorized",content = @Content(schema = @Schema(hidden = true))),
                            @ApiResponse(responseCode = "404", description = "Not found",content = @Content(schema = @Schema(hidden = true)))})
    public ResponseEntity<?> getAll(@PathVariable Integer id) {
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Добавление комментария к объявлению")
    @ApiResponses(value = {@ApiResponse(responseCode = "200",
            description = "Successfully retrieved",
            content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = Comment.class))}),
            @ApiResponse(responseCode = "401", description = "Unauthorized",content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(responseCode = "404", description = "Not found",content = @Content(schema = @Schema(hidden = true)))})
    @PostMapping("/{id}/comments")
    public ResponseEntity<?> addComment(@PathVariable Integer id, @RequestBody CreateOrUpdateComment createOrUpdateComment) {
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Удаление комментария")
    @ApiResponses(value = {@ApiResponse(responseCode = "200",
            description = "OK",
            content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(responseCode = "401", description = "Unauthorized",content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(responseCode = "403", description = "Forbidden",content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(responseCode = "404", description = "Not found",content = @Content(schema = @Schema(hidden = true)))})
    @DeleteMapping("/{adId}/comments/{commentId}")
    public ResponseEntity<?> delComment(@PathVariable Integer adId, @PathVariable Integer commentId) {
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Обновление комментария")
    @ApiResponses(value = {@ApiResponse(responseCode = "200",
            description = "Successfully retrieved",
            content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = Comment.class))}),
            @ApiResponse(responseCode = "401", description = "Unauthorized",content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(responseCode = "403", description = "Forbidden",content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(responseCode = "404", description = "Not found",content = @Content(schema = @Schema(hidden = true)))})
    @PatchMapping("/{adId}/comments/{commentId}")
    public ResponseEntity<?> login(@PathVariable Integer id, @PathVariable Integer commentId, @RequestBody CreateOrUpdateComment createOrUpdateComment) {
        return ResponseEntity.ok().build();
    }
}
