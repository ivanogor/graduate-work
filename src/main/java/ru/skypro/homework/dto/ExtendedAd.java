package ru.skypro.homework.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.skypro.homework.entity.AdEntity;
import ru.skypro.homework.entity.UserEntity;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExtendedAd {
    @Schema(description = "id объявления")
    private Integer pk;
    @Schema(description = "имя автора объявления")
    private String authorFirstName;
    @Schema(description = "фамилия автора объявления")
    private String authorLastName;
    @Schema(description = "описание объявления")
    private String description;
    @Schema(description = "логин автора объявления")
    private String email;
    @Schema(description = "ссылка на картинку объявления")
    private String image;
    @Schema(description = "телефон автора объявления")
    private String phone;
    @Schema(description = "цена объявления")
    private Integer price;
    @Schema(description = "заголовок объявления")
    private String title;

    public static ExtendedAd mapAdEntityToDto(AdEntity Ad, UserEntity user) {

        return ExtendedAd.builder()
                .pk(Ad.getPk())
                .description(Ad.getDescription())
                .price(Ad.getPrice())
                .title(Ad.getTitle())
                .authorFirstName(user.getFirstName())
                .authorLastName(user.getLastName())
                .phone(user.getPhone())
                .email(user.getEmail())
                .build();
    }
}
