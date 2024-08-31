package ru.skypro.homework.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.AdDto;
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
    public String updateImage(Integer id, MultipartFile image) {
        return "";
    }

    @Override
    public Boolean findById(Integer id) {
        return !repisitory.findById(id).isEmpty();
    }

    //temp method
    public AdDto mapAdToDto(Ad ad) {
        return AdDto.builder()
                .pk(ad.getPk())
                .title(ad.getTitle())
                .price(ad.getPrice())
                .build();
    }

    //temp method
    public Ad mapAdDtotoAd(AdDto dto) {
        return Ad.builder()
                .title(dto.getTitle())
                .price(dto.getPrice())
                .build();
    }

    //temp method
    public AdDto createNewAd(AdDto ad) {
        Ad newAd = createAds(mapAdDtotoAd(ad));
        return mapAdToDto(newAd);
    }
}
