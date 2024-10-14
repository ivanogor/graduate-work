package ru.skypro.homework.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

/**
 * DTO для запроса на смену пароля.
 * Этот класс используется для передачи данных о текущем и новом пароле между слоями приложения.
 */
@Data
@AllArgsConstructor
@Builder
public class NewPassword {

    /**
     * Текущий пароль пользователя.
     * Минимальная длина: 8 символов.
     * Максимальная длина: 16 символов.
     */
    @Schema(
            description = "Текущий пароль",
            minLength = 8,
            maxLength = 16
    )
    private String currentPassword;

    /**
     * Новый пароль пользователя.
     * Минимальная длина: 8 символов.
     * Максимальная длина: 16 символов.
     */
    @Schema(
            description = "Новый пароль",
            minLength = 8,
            maxLength = 16
    )
    private String newPassword;
}