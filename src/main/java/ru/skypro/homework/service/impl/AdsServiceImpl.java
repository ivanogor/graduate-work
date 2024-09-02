package ru.skypro.homework.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.Ad;
import ru.skypro.homework.dto.Ads;
import ru.skypro.homework.dto.CreateOrUpdateAd;
import ru.skypro.homework.dto.ExtendedAd;
import ru.skypro.homework.entity.AdEntity;
import ru.skypro.homework.repository.AdEntityRepisitory;
import ru.skypro.homework.service.AdsService;

@Service
@RequiredArgsConstructor
public class AdsServiceImpl implements AdsService {
    private final AdEntityRepisitory repisitory;

    @Override
    public AdEntity createAds(AdEntity adEntity) {
        return repisitory.save(adEntity);
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
        repisitory.deleteById(id);
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
        return !repisitory.findById(id).isEmpty();
    }

    //temp method
    public Ad mapAdToDto(AdEntity adEntity) {
        return Ad.builder()
                .pk(adEntity.getPk())
                .title(adEntity.getTitle())
                .price(adEntity.getPrice())
                .build();
    }

    //temp method
    public AdEntity mapAdDtotoAd(Ad ad) {
        return AdEntity.builder()
                .title(ad.getTitle())
                .price(ad.getPrice())
                .build();
    }

    //temp method
    public Ad createNewAd(Ad ad) {
        AdEntity newAdEntity = createAds(mapAdDtotoAd(ad));
        return mapAdToDto(newAdEntity);
    }
}
