package ru.skypro.homework.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.skypro.homework.entity.AdEntity;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Ad {
    @Schema(description = "id автора объявления")
    private Long author;
    @Schema(description = "ссылка на картинку объявления")
    private String image;
    @Schema(description = "id объявления")
    private Integer pk;
    @Schema(description = "цена объявления")
    private Integer price;
    @Schema(description = "заголовок объявления")
    private String title;

    public static Ad mapEntityToDto(AdEntity ad) {
        return Ad.builder()
                .title(ad.getTitle())
                .price(ad.getPrice())
                .pk(ad.getPk())
                .author(ad.getAuthor())
                .image(ad.getImage())
                .build();
    }

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