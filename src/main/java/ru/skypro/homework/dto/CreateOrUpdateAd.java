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
public class CreateOrUpdateAd {
    @Schema(description = "заголовок объявления", minLength = 4, maxLength = 32)
    private String title;
    @Schema(description = "цена объявления", minimum = "0", maximum = "10000000", example = "10000000")
    private Integer price;
    @Schema(description = "описание объявления", minLength = 8, maxLength = 64, example = "stringst")
    private String description;

    public AdEntity mapDtoToAdEntity(String imageLink, Long author) {
        return AdEntity.builder()
                .image(imageLink)
                .price(price)
                .description(description)
                .author(author)
                .title(title)
                .build();
    }

    public Ad updateAd(AdEntity ad) {
        ad.setTitle(title);
        ad.setPrice(price);
        ad.setDescription(description);
        return Ad.mapEntityToDto(ad);
    }
}
