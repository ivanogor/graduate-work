package ru.skypro.homework.service;

import org.springframework.security.core.Authentication;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.Ad;
import ru.skypro.homework.dto.Ads;
import ru.skypro.homework.dto.CreateOrUpdateAd;
import ru.skypro.homework.dto.ExtendedAd;
import ru.skypro.homework.entity.UserEntity;

import java.io.IOException;

public interface AdsService {
    Ad createAds(CreateOrUpdateAd createAd, MultipartFile image, Authentication authentication)
            throws IOException;

    ExtendedAd getExtendedAd(Integer id, Authentication authentication);

    Ads getAdsDto();

    void deleteAd(Integer id, Authentication authentication);

    Ad updateAd(Integer id, CreateOrUpdateAd adDto, Authentication authentication);

    Ads getAdsByUser(Authentication authentication);

    byte[] updateImage(Integer id, MultipartFile image, Authentication authentication);

    Boolean findById(Integer id);

    UserEntity handleUser(Authentication authentication);
}
