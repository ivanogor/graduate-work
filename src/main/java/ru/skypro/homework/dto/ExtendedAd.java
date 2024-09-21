package ru.skypro.homework.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.skypro.homework.entity.AdEntity;
import ru.skypro.homework.entity.UserEntity;

/**
 * DTO для представления расширенной информации об объявлении.
 * Этот класс используется для передачи расширенных данных об объявлении между слоями приложения.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExtendedAd {

    /**
     * ID объявления.
     */
    @Schema(description = "ID объявления")
    private Integer pk;

    /**
     * Имя автора объявления.
     */
    @Schema(description = "Имя автора объявления")
    private String authorFirstName;

    /**
     * Фамилия автора объявления.
     */
    @Schema(description = "Фамилия автора объявления")
    private String authorLastName;

    /**
     * Описание объявления.
     */
    @Schema(description = "Описание объявления")
    private String description;

    /**
     * Логин автора объявления.
     */
    @Schema(description = "Логин автора объявления")
    private String email;

    /**
     * Ссылка на картинку объявления.
     */
    @Schema(description = "Ссылка на картинку объявления")
    private String image;

    /**
     * Телефон автора объявления.
     */
    @Schema(description = "Телефон автора объявления")
    private String phone;

    /**
     * Цена объявления.
     */
    @Schema(description = "Цена объявления")
    private Integer price;

    /**
     * Заголовок объявления.
     */
    @Schema(description = "Заголовок объявления")
    private String title;

    /**
     * Преобразует сущность объявления и сущность пользователя в DTO с расширенной информацией.
     *
     * @param ad Сущность объявления.
     * @param user Сущность пользователя.
     * @return DTO с расширенной информацией об объявлении.
     */
    public static ExtendedAd mapAdEntityToDto(AdEntity ad, UserEntity user) {
        return ExtendedAd.builder()
                .pk(ad.getPk())
                .description(ad.getDescription())
                .price(ad.getPrice())
                .title(ad.getTitle())
                .authorFirstName(user.getFirstName())
                .authorLastName(user.getLastName())
                .phone(user.getPhone())
                .email(user.getEmail())
                .image(ad.getImage())
                .build();
    }
}