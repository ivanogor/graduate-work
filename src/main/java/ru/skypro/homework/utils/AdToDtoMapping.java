package ru.skypro.homework.utils;

import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.AdDto;
import ru.skypro.homework.entity.Ad;

@Service
public class AdToDtoMapping {
    public AdDto mapAdToDto(Ad ad) {
        return AdDto.builder()
                .title(ad.getTitle())
                .price(ad.getPrice())
                .pk(ad.getPk())
                .author(ad.getAuthor())
                .build();
    }

    public Ad mapDtoToAd(AdDto dto) {
        return Ad.builder()
                .title(dto.getTitle())
                .price(dto.getPrice())
                .pk(dto.getPk())
                .author(dto.getAuthor())
                .build();
    }
}
