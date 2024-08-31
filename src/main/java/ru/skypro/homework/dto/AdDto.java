package ru.skypro.homework.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.skypro.homework.entity.Ad;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AdDto {
    private Integer pk;
    private Integer author;
    private Integer price;
    private String title;
    private byte[] image;

    public static AdDto toDto(Ad ad) {
        return AdDto.builder()
                .title(ad.getTitle())
                .price(ad.getPrice())
                .pk(ad.getPk())
                .author(ad.getAuthor())
                .build();
    }

    public Ad toEntity() {
        return Ad.builder()
                .title(title)
                .price(price)
                .pk(pk)
                .author(author)
                .build();
    }
}
