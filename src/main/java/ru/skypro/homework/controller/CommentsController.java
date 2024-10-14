package ru.skypro.homework.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import ru.skypro.homework.dto.Comment;
import ru.skypro.homework.dto.Comments;
import ru.skypro.homework.dto.CreateOrUpdateComment;
import ru.skypro.homework.entity.Role;
import ru.skypro.homework.entity.UserEntity;
import ru.skypro.homework.service.impl.CommentsServiceImpl;
import ru.skypro.homework.utils.AdServiceUtils;

/**
 * Контроллер для управления комментариями к объявлениям.
 * Предоставляет методы для получения, добавления, обновления и удаления комментариев.
 */
@Slf4j
@CrossOrigin(value = "http://localhost:3000")
@RestController
@RequiredArgsConstructor
@Tag(name = "Комментарии")
@RequestMapping("ads")
public class CommentsController {
    private final CommentsServiceImpl commentsService;
    private final AdServiceUtils adUtils;

    /**
     * Получает все комментарии к указанному объявлению.
     *
     * @param id Идентификатор объявления.
     * @return Список комментариев к объявлению.
     */
    @GetMapping("/{id}/comments")
    @Operation(summary = "Получение комментариев объявления")
    @ApiResponses(value = {@ApiResponse(responseCode = "200",
            description = "Successfully retrieved",
            content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = Comments.class))}),
            @ApiResponse(responseCode = "401", description = "Unauthorized",content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(responseCode = "404", description = "Not found",content = @Content(schema = @Schema(hidden = true)))})
    public ResponseEntity<Comments> getAll(@PathVariable Long id) {
        Comments comments = commentsService.getAll(id);
        return ResponseEntity.status(HttpStatus.CREATED).body(comments);
    }

    /**
     * Добавляет новый комментарий к указанному объявлению.
     *
     * @param id Идентификатор объявления.
     * @param createOrUpdateComment Данные для создания комментария.
     * @param authentication Аутентификация пользователя.
     * @return Созданный комментарий.
     */
    @Operation(summary = "Добавление комментария к объявлению")
    @ApiResponses(value = {@ApiResponse(responseCode = "200",
            description = "Successfully retrieved",
            content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = Comment.class))}),
            @ApiResponse(responseCode = "401", description = "Unauthorized",content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(responseCode = "404", description = "Not found",content = @Content(schema = @Schema(hidden = true)))})
    @PostMapping("/{id}/comments")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Comment> addComment(@PathVariable Long id, @RequestBody CreateOrUpdateComment createOrUpdateComment,
                                              Authentication authentication) {
        Comment comment = commentsService.createComment(id,createOrUpdateComment,authentication);
        return ResponseEntity.status(HttpStatus.CREATED).body(comment);
    }

    /**
     * Удаляет комментарий по его идентификатору.
     *
     * @param adId Идентификатор объявления.
     * @param commentId Идентификатор комментария.
     * @param authentication Аутентификация пользователя.
     * @return Ответ об успешном удалении или ошибке доступа.
     */
    @Operation(summary = "Удаление комментария")
    @ApiResponses(value = {@ApiResponse(responseCode = "200",
            description = "OK",
            content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(responseCode = "401", description = "Unauthorized",content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(responseCode = "403", description = "Forbidden",content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(responseCode = "404", description = "Not found",content = @Content(schema = @Schema(hidden = true)))})
    @DeleteMapping("/{adId}/comments/{commentId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> delComment(@PathVariable Integer adId, @PathVariable Integer commentId,
                                        Authentication authentication) {
        if (!commentsService.foundById(commentId)) {
            return ResponseEntity.notFound().build();
        } else if (!checkAccess(authentication, commentId) && !getRole(authentication).equals(Role.ADMIN)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        } else {
            commentsService.deleteComment(commentId, authentication);
            return ResponseEntity.ok().build();
        }
    }

    /**
     * Обновляет существующий комментарий.
     *
     * @param adId Идентификатор объявления.
     * @param commentId Идентификатор комментария.
     * @param createOrUpdateComment Новые данные для комментария.
     * @param authentication Аутентификация пользователя.
     * @return Обновленный комментарий.
     */
    @Operation(summary = "Обновление комментария")
    @ApiResponses(value = {@ApiResponse(responseCode = "200",
            description = "Successfully retrieved",
            content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = Comment.class))}),
            @ApiResponse(responseCode = "401", description = "Unauthorized",content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(responseCode = "403", description = "Forbidden",content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(responseCode = "404", description = "Not found",content = @Content(schema = @Schema(hidden = true)))})
    @PatchMapping("/{adId}/comments/{commentId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Comment> updateComment(@PathVariable Integer adId, @PathVariable Integer commentId, @RequestBody CreateOrUpdateComment createOrUpdateComment,
                                                 Authentication authentication) {
        if (!commentsService.foundById(commentId)) {
            return ResponseEntity.notFound().build();
        } else if (!checkAccess(authentication, commentId) && !getRole(authentication).equals(Role.ADMIN)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        } else {
            Comment comment=commentsService.updateComment(commentId,createOrUpdateComment,authentication);
            return ResponseEntity.ok().body(comment);
        }
    }

    private boolean checkAccess(Authentication authentication, Integer commentId) {
        Long userId = adUtils.handleUser(authentication).getId();
        Long authorId = commentsService.findById(commentId).getAuthor();
        return userId.equals(authorId);
    }

    private Role getRole(Authentication authentication) {
        UserEntity user = adUtils.handleUser(authentication);
        return user.getRole();
    }
}