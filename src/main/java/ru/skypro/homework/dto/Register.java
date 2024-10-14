package ru.skypro.homework.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.skypro.homework.entity.Role;
import ru.skypro.homework.entity.UserEntity;

/**
 * DTO для регистрации нового пользователя.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Register {
    @Schema(description = "логин", minLength = 4, maxLength = 32)
    private String username;

    @Schema(description = "пароль", minLength = 8, maxLength = 16)
    private String password;

    @Schema(description = "имя пользователя", minLength = 2, maxLength = 16)
    private String firstName;

    @Schema(description = "фамилия пользователя", minLength = 2, maxLength = 16)
    private String lastName;

    @Schema(description = "телефон пользователя", pattern = "\\+7\\s?\\(?\\d{3}\\)?\\s?\\d{3}-?\\d{2}-?\\d{2}",
            example = "+7(378) 54503-99")
    private String phone;

    @Schema(description = "роль пользователя")
    private Role role;

    /**
     * Преобразует DTO регистрации в сущность пользователя.
     *
     * @return Сущность пользователя.
     */
    public UserEntity toUserEntity() {
        return UserEntity
                .builder()
                .username(this.username)
                .email(this.username)
                .firstName(this.firstName)
                .lastName(this.lastName)
                .phone(this.phone)
                .role(this.role)
                .build();
    }
}