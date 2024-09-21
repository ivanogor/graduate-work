package ru.skypro.homework.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.skypro.homework.entity.UserEntity;

/**
 * DTO для обновления информации о пользователе.
 * Этот класс используется для передачи данных о новой информации пользователя между слоями приложения.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateUser {

    /**
     * Имя пользователя.
     * Минимальная длина: 3 символа.
     * Максимальная длина: 10 символов.
     */
    @Schema(
            description = "Имя пользователя",
            minLength = 3,
            maxLength = 10
    )
    private String firstName;

    /**
     * Фамилия пользователя.
     * Минимальная длина: 3 символа.
     * Максимальная длина: 10 символов.
     */
    @Schema(
            description = "Фамилия пользователя",
            minLength = 3,
            maxLength = 10
    )
    private String lastName;

    /**
     * Телефон пользователя.
     * Пример: "+7 (999) 123-45-67".
     * Формат: "+7\\s?\\(?\\d{3}\\)?\\s?\\d{3}-?\\d{2}-?\\d{2}".
     */
    @Schema(
            description = "Телефон пользователя",
            example = "+7 (999) 123-45-67",
            pattern = "\\+7\\s?\\(?\\d{3}\\)?\\s?\\d{3}-?\\d{2}-?\\d{2}"
    )
    private String phone;

    /**
     * Преобразует сущность пользователя в DTO для обновления информации.
     *
     * @param userEntity Сущность пользователя.
     * @return DTO для обновления информации о пользователе.
     */
    public static UpdateUser toDto(UserEntity userEntity) {
        return UpdateUser.builder()
                .firstName(userEntity.getFirstName())
                .lastName(userEntity.getLastName())
                .phone(userEntity.getPhone())
                .build();
    }
}