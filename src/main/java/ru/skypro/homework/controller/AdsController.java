package ru.skypro.homework.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.AdDto;
import ru.skypro.homework.dto.AdsDto;
import ru.skypro.homework.dto.CreateOrUpdateAdDto;
import ru.skypro.homework.dto.ExtendedAdDto;
import ru.skypro.homework.entity.Ad;
import ru.skypro.homework.service.impl.AdsServiceImpl;

import static org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE;

@RestController
@CrossOrigin(value = "http://localhost:3000")
@RequestMapping("ads")
@Tag(name = "Объявления")
public class AdsController {
    private final AdsServiceImpl adsSevice;

    public AdsController(AdsServiceImpl adsSevice) {
        this.adsSevice = adsSevice;
    }
    @Operation(summary = "Добавление объявления")
    @PostMapping(consumes = MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<AdDto> createAds(@RequestPart("image") MultipartFile image,
                                           @RequestPart("properties") AdDto ad) {
        AdDto response = adsSevice.createNewAd(ad);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    @Operation(summary = "Получение информации об объявлении")
    @GetMapping("{id}")
    public ResponseEntity<ExtendedAdDto> getExtendedAd(@PathVariable Integer id) {
        ExtendedAdDto response = adsSevice.getExtendedAd(id);
        if (!adsSevice.findById(id)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(response);
    }
    @Operation(summary = "Получение всех объявлений")
    @GetMapping
    public ResponseEntity<AdsDto> getAdsDto() {
        AdsDto response = adsSevice.getAdsDto();
        return ResponseEntity.ok().body(response);
    }
    @Operation(summary = "Удаление объявления")
    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteAd(@PathVariable Integer id) {
        if (!adsSevice.findById(id)) {
            return ResponseEntity.notFound().build();
        }
        adsSevice.deleteAd(id);
        return ResponseEntity.ok().build();
    }
    @Operation(summary = "Обновление информации об объявлении")
    @PatchMapping("{id}")
    public ResponseEntity<Ad> updateAd(@PathVariable Integer id, @RequestBody CreateOrUpdateAdDto adDto) {
        if (!adsSevice.findById(id)) {
            return ResponseEntity.notFound().build();
        }
        Ad response = adsSevice.updateAd(id, adDto);
        return ResponseEntity.ok().body(response);
    }
    @Operation(summary = "Получение объявлений авторизованного пользователя")
    @GetMapping("/me")
    public ResponseEntity<AdsDto> getAdsByUser() {
        AdsDto response = adsSevice.getAdsByUser();
        return ResponseEntity.ok().body(response);
    }
    @Operation(summary = "Обновление картинки объявления")
    @PatchMapping(value = "{id}/image", consumes = MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> updateImage(@PathVariable Integer id,
                                         @RequestParam("image") MultipartFile image) {
        if (!adsSevice.findById(id)) {
            return ResponseEntity.notFound().build();
        }
        String response = adsSevice.updateImage(id, image);
        return ResponseEntity.ok().body(response);
    }
}
