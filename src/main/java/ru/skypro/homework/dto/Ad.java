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
    private Integer author;
    @Schema(description = "ссылка на картинку объявления")
    private String image;
    @Schema(description = "id объявления")
    private Integer pk;
    @Schema(description = "цена объявления")
    private Integer price;
    @Schema(description = "заголовок объявления")
    private String title;

    public static Ad toDto(AdEntity ad) {
        return Ad.builder()
                .title(ad.getTitle())
                .price(ad.getPrice())
                .pk(ad.getPk())
                .author(ad.getAuthor())
                .build();
    }

    public AdEntity toEntity() {
        return AdEntity.builder()
                .title(title)
                .price(price)
                .pk(pk)
                .author(author)
                .build();
    }
}