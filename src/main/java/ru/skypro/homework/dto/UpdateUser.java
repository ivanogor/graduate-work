package ru.skypro.homework.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.skypro.homework.entity.UserEntity;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateUser {
    @Schema(
            description = "имя пользователя",
            minLength = 3,
            maxLength = 10
    )
    private String firstName;

    @Schema(
            description = "фамилия пользователя",
            minLength = 3,
            maxLength = 10
    )
    private String lastName;

    @Schema(
            description = "телефон пользователя",
            example = "+7 (999) 123-45-67",
            pattern = "\\+7\\s?\\(?\\d{3}\\)?\\s?\\d{3}-?\\d{2}-?\\d{2}"
    )
    private String phone;

    public static UpdateUser toDto(UserEntity userEntity) {
        return UpdateUser.builder()
                .firstName(userEntity.getFirstName())
                .lastName(userEntity.getLastName())
                .phone(userEntity.getPhone())
                .build();
    }
}
