package ru.skypro.homework.service;

import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.Ad;
import ru.skypro.homework.dto.Ads;
import ru.skypro.homework.dto.CreateOrUpdateAd;
import ru.skypro.homework.dto.ExtendedAd;
import ru.skypro.homework.entity.AdEntity;

public interface AdsService {
    AdEntity createAds(AdEntity adEntity);

    ExtendedAd getExtendedAd(Integer id);

    Ads getAdsDto();

    void deleteAd(Integer id);

    Ad updateAd(Integer id, CreateOrUpdateAd adDto);

    Ads getAdsByUser();

    byte[] updateImage(Integer id, MultipartFile image);

    Boolean findById(Integer id);
}
