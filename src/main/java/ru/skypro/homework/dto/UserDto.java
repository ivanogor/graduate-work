package ru.skypro.homework.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.skypro.homework.entity.Role;
import ru.skypro.homework.entity.User;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto {
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

    public static UserDto toDto(User user){
        return UserDto.builder()
                .email(user.getEmail())
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .phone(user.getPhone())
                .role(user.getRole())
                .image(user.getImage())
                .build();
    }

    public User toEntity(){
        return User.builder()
                .email(email)
                .firstName(firstName)
                .lastName(lastName)
                .phone(phone)
                .role(role)
                .image(image)
                .build();
    }
}
