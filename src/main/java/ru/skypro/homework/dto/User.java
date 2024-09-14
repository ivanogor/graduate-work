package ru.skypro.homework.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.skypro.homework.entity.Role;
import ru.skypro.homework.entity.UserEntity;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    @Schema(
            description = "id пользователя"
    )
    private Long id;

    @Schema(
            description = "логин пользователя"
    )
    private String email;

    @Schema(
            description = "имя пользователя"
    )
    private String firstName;

    @Schema(
            description = "фамилия пользователя"
    )
    private String lastName;

    @Schema(
            description = "id пользователя"
    )
    private String phone;

    @Schema(
            description = "роль пользователя", implementation = Role.class
    )
    private Role role;

    @Schema(
            description = "ссылка на аватар пользователя"
    )
    private String image;

    public static User toDto(UserEntity userEntity) {
        return User.builder()
                .email(userEntity.getEmail())
                .id(userEntity.getId())
                .firstName(userEntity.getFirstName())
                .lastName(userEntity.getLastName())
                .phone(userEntity.getPhone())
                .role(userEntity.getRole())
                .image(userEntity.getImage())
                .build();
    }

    public UserEntity toEntity() {
        return UserEntity.builder()
                .email(email)
                .firstName(firstName)
                .lastName(lastName)
                .phone(phone)
                .role(role)
                .image(image)
                .enabled(true)
                .build();
    }
}