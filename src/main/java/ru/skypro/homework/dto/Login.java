package ru.skypro.homework.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO для представления данных для входа (логин и пароль).
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Login {
    @Schema(description = "логин", required = true, minLength = 8, maxLength = 16)
    private String username;

    @Schema(description = "пароль", required = true, minLength = 8, maxLength = 16)
    private String password;
}