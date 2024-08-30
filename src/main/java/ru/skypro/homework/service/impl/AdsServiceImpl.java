package ru.skypro.homework.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.AdsDto;
import ru.skypro.homework.dto.CreateOrUpdateAdDto;
import ru.skypro.homework.dto.ExtendedAdDto;
import ru.skypro.homework.entity.Ad;
import ru.skypro.homework.repository.AdRepisitory;
import ru.skypro.homework.service.AdsService;

@Service
@RequiredArgsConstructor
public class AdsServiceImpl implements AdsService {
    private final AdRepisitory repisitory;

    @Override
    public Ad createAds(Ad ad) {
        return repisitory.save(ad);
    }

    @Override
    public ExtendedAdDto getExtendedAd(Integer id) {
        return null;
    }

    @Override
    public AdsDto getAdsDto() {
        return null;
    }

    @Override
    public void deleteAd(Integer id) {
        repisitory.deleteById(id);
    }

    @Override
    public Ad updateAd(Integer id, CreateOrUpdateAdDto adDto) {
        return null;
    }

    @Override
    public AdsDto getAdsByUser() {
        return null;
    }

    @Override
    public String updateImage(Integer id, String imageLink) {
        return "";
    }
}
