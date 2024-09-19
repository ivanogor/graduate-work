package ru.skypro.homework.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.skypro.homework.entity.AdEntity;

/**
 * DTO для представления объявления.
 * Этот класс используется для передачи данных объявления между слоями приложения.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Ad {

    /**
     * ID автора объявления.
     */
    @Schema(description = "ID автора объявления")
    private Long author;

    /**
     * Ссылка на картинку объявления.
     */
    @Schema(description = "Ссылка на картинку объявления")
    private String image;

    /**
     * ID объявления.
     */
    @Schema(description = "ID объявления")
    private Integer pk;

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
     * Преобразует сущность объявления в DTO.
     *
     * @param ad Сущность объявления.
     * @return DTO объявления.
     */
    public static Ad mapEntityToDto(AdEntity ad) {
        return Ad.builder()
                .title(ad.getTitle())
                .price(ad.getPrice())
                .pk(ad.getPk())
                .author(ad.getAuthor())
                .image(ad.getImage())
                .build();
    }

    /**
     * Преобразует DTO объявления в сущность.
     *
     * @param description Описание объявления.
     * @return Сущность объявления.
     */
    public AdEntity mapDtoToEntity(String description) {
        return AdEntity.builder()
                .title(title)
                .price(price)
                .image(image)
                .pk(pk)
                .author(author)
                .description(description)
                .build();
    }
}