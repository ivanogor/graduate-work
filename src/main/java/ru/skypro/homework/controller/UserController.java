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
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.NewPassword;
import ru.skypro.homework.dto.UpdateUser;
import ru.skypro.homework.dto.User;
import ru.skypro.homework.service.UserService;

import static org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE;

/**
 * Контроллер для управления пользователями.
 * Предоставляет методы для обновления пароля, получения и обновления информации о текущем пользователе,
 * а также для обновления аватара пользователя.
 */
@Slf4j
@CrossOrigin(value = "http://localhost:3000")
@RestController
@RequiredArgsConstructor
@Tag(name = "Пользователи")
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    /**
     * Обновляет пароль текущего пользователя.
     *
     * @param newPassword Новый пароль.
     * @return Ответ об успешном обновлении пароля или ошибке доступа.
     */
    @PostMapping("/set_password")
    @Operation(summary = "Обновление пароля")
    @ApiResponses(value = {@ApiResponse(responseCode = "200",
            description = "OK",
            content = {@Content(
                    schema = @Schema(hidden = true))}),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content(schema = @Schema(hidden = true)))})
    ResponseEntity<?> setPassword(@RequestBody(required = false) NewPassword newPassword) {
        log.info("Invoked setPassword");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            log.error("setPassword return UNAUTHORIZED");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        String username = authentication.getName();
        if (!userService.hasAccessToChangePassword(username)) {
            log.error("setPassword return FORBIDDEN");
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        userService.setPassword(newPassword);
        return ResponseEntity.ok().build();
    }

    /**
     * Получает информацию о текущем авторизованном пользователе.
     *
     * @return Информация о пользователе.
     */
    @GetMapping("/me")
    @Operation(summary = "Получение информации об авторизованном пользователе")
    @ApiResponses(value = {@ApiResponse(responseCode = "200",
            description = "OK",
            content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = User.class))}),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(schema = @Schema(hidden = true)))})
    ResponseEntity<User> getCurrentUser() {
        log.info("Invoked getCurrentUser");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            log.error("getCurrentUser return UNAUTHORIZED");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        return ResponseEntity.ok(userService.getCurrentUser());
    }

    /**
     * Обновляет информацию о текущем авторизованном пользователе.
     *
     * @param updateUser Обновленная информация о пользователе.
     * @return Обновленная информация о пользователе.
     */
    @PatchMapping("/me")
    @Operation(summary = "Обновление информации об авторизованном пользователе")
    @ApiResponses(value = {@ApiResponse(responseCode = "200",
            description = "OK",
            content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = UpdateUser.class))}),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(schema = @Schema(hidden = true)))})
    ResponseEntity<UpdateUser> updateUser(@RequestBody(required = false) UpdateUser updateUser) {
        log.info("Invoked updateUser");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            log.error("updateUser return UNAUTHORIZED");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        return ResponseEntity.ok(userService.updateUser(updateUser));
    }

    /**
     * Обновляет аватар текущего авторизованного пользователя.
     *
     * @param image Новый аватар пользователя.
     * @return Ответ об успешном обновлении аватара или ошибке доступа.
     */
    @PatchMapping(value = "/me/image", consumes = MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "Обновление аватара авторизованного пользователя")
    @ApiResponses(value = {@ApiResponse(responseCode = "200",
            description = "OK",
            content = {@Content(
                    schema = @Schema(hidden = true))}),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(schema = @Schema(hidden = true)))})
    public ResponseEntity<?> updateUserAvatar(@RequestParam("image") MultipartFile image) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            log.error("updateUserAvatar return UNAUTHORIZED");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        userService.updateUserAvatar(image);
        return ResponseEntity.ok().build();
    }
}