package ru.skypro.homework.service;

import ru.skypro.homework.dto.AdsDto;
import ru.skypro.homework.dto.CreateOrUpdateAdDto;
import ru.skypro.homework.dto.ExtendedAdDto;
import ru.skypro.homework.entity.Ad;

public interface AdsService {
    Ad createAds(Ad ad);

    ExtendedAdDto getExtendedAd(Integer id);

    AdsDto getAdsDto();

    void deleteAd(Integer id);

    Ad updateAd(Integer id, CreateOrUpdateAdDto adDto);

    AdsDto getAdsByUser();

    String updateImage(Integer id, String imageLink);
}
