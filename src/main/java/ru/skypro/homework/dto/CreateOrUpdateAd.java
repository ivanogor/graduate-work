package ru.skypro.homework.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.skypro.homework.entity.AdEntity;

/**
 * DTO для создания или обновления объявления.
 * Этот класс используется для передачи данных о новом или обновленном объявлении между слоями приложения.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateOrUpdateAd {

    /**
     * Заголовок объявления.
     * Минимальная длина: 4 символа.
     * Максимальная длина: 32 символа.
     */
    @Schema(description = "Заголовок объявления", minLength = 4, maxLength = 32)
    private String title;

    /**
     * Цена объявления.
     * Минимальное значение: 0.
     * Максимальное значение: 10000000.
     * Пример: 10000000.
     */
    @Schema(description = "Цена объявления", minimum = "0", maximum = "10000000", example = "10000000")
    private Integer price;

    /**
     * Описание объявления.
     * Минимальная длина: 8 символов.
     * Максимальная длина: 64 символа.
     * Пример: "stringst".
     */
    @Schema(description = "Описание объявления", minLength = 8, maxLength = 64, example = "stringst")
    private String description;

    /**
     * Преобразует DTO в сущность объявления.
     *
     * @param imageLink Ссылка на изображение объявления.
     * @param author ID автора объявления.
     * @return Сущность объявления.
     */
    public AdEntity mapDtoToAdEntity(String imageLink, Long author) {
        return AdEntity.builder()
                .image(imageLink)
                .price(price)
                .description(description)
                .author(author)
                .title(title)
                .build();
    }

    /**
     * Обновляет существующую сущность объявления данными из DTO.
     *
     * @param ad Сущность объявления для обновления.
     * @return Обновленное DTO объявления.
     */
    public Ad updateAd(AdEntity ad) {
        ad.setTitle(title);
        ad.setPrice(price);
        ad.setDescription(description);
        return Ad.mapEntityToDto(ad);
    }
}