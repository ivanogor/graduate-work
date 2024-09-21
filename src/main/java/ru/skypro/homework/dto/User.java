package ru.skypro.homework.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.skypro.homework.entity.Role;
import ru.skypro.homework.entity.UserEntity;

/**
 * DTO для представления информации о пользователе.
 * Этот класс используется для передачи данных о пользователе между слоями приложения.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    /**
     * ID пользователя.
     */
    @Schema(
            description = "ID пользователя"
    )
    private Long id;

    /**
     * Логин пользователя.
     */
    @Schema(
            description = "Логин пользователя"
    )
    private String email;

    /**
     * Имя пользователя.
     */
    @Schema(
            description = "Имя пользователя"
    )
    private String firstName;

    /**
     * Фамилия пользователя.
     */
    @Schema(
            description = "Фамилия пользователя"
    )
    private String lastName;

    /**
     * Телефон пользователя.
     */
    @Schema(
            description = "Телефон пользователя"
    )
    private String phone;

    /**
     * Роль пользователя.
     */
    @Schema(
            description = "Роль пользователя", implementation = Role.class
    )
    private Role role;

    /**
     * Ссылка на аватар пользователя.
     */
    @Schema(
            description = "Ссылка на аватар пользователя"
    )
    private String image;

    /**
     * Преобразует сущность пользователя в DTO.
     *
     * @param userEntity Сущность пользователя.
     * @return DTO пользователя.
     */
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

    /**
     * Преобразует DTO в сущность пользователя.
     *
     * @return Сущность пользователя.
     */
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