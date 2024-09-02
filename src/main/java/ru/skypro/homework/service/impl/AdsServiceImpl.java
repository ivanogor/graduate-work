package ru.skypro.homework.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.Ad;
import ru.skypro.homework.dto.Ads;
import ru.skypro.homework.dto.CreateOrUpdateAd;
import ru.skypro.homework.dto.ExtendedAd;
import ru.skypro.homework.entity.AdEntity;
import ru.skypro.homework.repository.AdEntityRepository;
import ru.skypro.homework.service.AdsService;

@Service
@RequiredArgsConstructor
public class AdsServiceImpl implements AdsService {
    private final AdEntityRepository repository;

    @Override
    public AdEntity createAds(AdEntity adEntity) {
        return repository.save(adEntity);
    }

    @Override
    public ExtendedAd getExtendedAd(Integer id) {
        return null;
    }

    @Override
    public Ads getAdsDto() {
        return null;
    }

    @Override
    public void deleteAd(Integer id) {
        repository.deleteById(id);
    }

    @Override
    public Ad updateAd(Integer id, CreateOrUpdateAd ad) {
        return null;
    }

    @Override
    public Ads getAdsByUser() {
        return null;
    }

    @Override
    public byte[] updateImage(Integer id, MultipartFile image) {
        return null;
    }

    @Override
    public Boolean findById(Integer id) {
        return !repository.findById(id).isEmpty();
    }
}
