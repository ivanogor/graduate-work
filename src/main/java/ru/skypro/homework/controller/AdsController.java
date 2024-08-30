package ru.skypro.homework.controller;

import org.springframework.web.bind.annotation.*;
import ru.skypro.homework.dto.AdsDto;
import ru.skypro.homework.dto.CreateOrUpdateAdDto;
import ru.skypro.homework.dto.ExtendedAdDto;
import ru.skypro.homework.entity.Ad;
import ru.skypro.homework.service.impl.AdsServiceImpl;

@RestController
@CrossOrigin(value = "http://localhost:3000")
@RequestMapping("ads")
public class AdsController {
    private final AdsServiceImpl adsSevice;

    public AdsController(AdsServiceImpl adsSevice) {
        this.adsSevice = adsSevice;
    }

    @PostMapping
    public Ad createAds(@RequestBody Ad ad) {
        return adsSevice.createAds(ad);
    }

    @GetMapping("{id}")
    public ExtendedAdDto getExtendedAd(@PathVariable Integer id) {
        return adsSevice.getExtendedAd(id);
    }

    @GetMapping
    public AdsDto getAdsDTO() {
        return adsSevice.getAdsDto();
    }

    @DeleteMapping("{id}")
    public void deleteAd(@PathVariable Integer id) {
        adsSevice.deleteAd(id);
    }

    @PatchMapping("{id}")
    public Ad updateAd(@PathVariable Integer id, @RequestBody CreateOrUpdateAdDto adDto) {
        return adsSevice.updateAd(id, adDto);
    }

    @GetMapping("/me")
    public AdsDto getAdsByUser() {
        return adsSevice.getAdsByUser();
    }

    @PatchMapping("{id}/image")
    public String updateImage(@PathVariable Integer id, String imageLink) {
        return adsSevice.updateImage(id, imageLink);
    }
}
