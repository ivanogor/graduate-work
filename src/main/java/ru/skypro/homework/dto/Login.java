package ru.skypro.homework.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * DTO для представления данных для входа (логин и пароль).
 */
@Data
public class Login {
    @Schema(description = "логин", required = true, minLength = 8, maxLength = 16)
    private String username;

    @Schema(description = "пароль", required = true, minLength = 8, maxLength = 16)
    private String password;
}