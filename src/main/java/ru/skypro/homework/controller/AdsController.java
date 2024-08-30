package ru.skypro.homework.controller;

/*import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;*/
import org.springframework.web.bind.annotation.*;
import ru.skypro.homework.dto.AdsDto;
import ru.skypro.homework.dto.CreateOrUpdateAdDto;
import ru.skypro.homework.dto.ExtendedAdDto;
import ru.skypro.homework.entity.Ad;
import ru.skypro.homework.service.impl.AdsServiceImpl;

@RestController
@CrossOrigin(value = "http://localhost:3000")
//@Tag(name = "API для работы с объявлениями")
@RequestMapping("ads")
public class AdsController {
    private final AdsServiceImpl adsSevice;

    public AdsController(AdsServiceImpl adsSevice) {
        this.adsSevice = adsSevice;
    }

    @PostMapping
//    @Operation(summary = "Добавление объявления")
    public Ad createAds(@RequestBody Ad ad) {
        return adsSevice.createAds(ad);
    }

    @GetMapping("{id}")
//    @Operation(summary = "Получение информации об объявлении")
    public ExtendedAdDto getExtendedAd(@PathVariable Integer id) {
        return adsSevice.getExtendedAd(id);
    }

    @GetMapping
//    @Operation(summary = "Получение всех объявлений")
    public AdsDto getAdsDTO() {
        return adsSevice.getAdsDto();
    }

    @DeleteMapping("{id}")
//    @Operation(summary = "Удаление объявления")
    public void deleteAd(@PathVariable Integer id) {
        adsSevice.deleteAd(id);
    }

    @PatchMapping("{id}")
//    @Operation(summary = "Обновление информации об объявлении")
    public Ad updateAd(@PathVariable Integer id, @RequestBody CreateOrUpdateAdDto adDto) {
        return adsSevice.updateAd(id, adDto);
    }

    @GetMapping("/me")
//    @Operation(summary = "Получение объявлений авторизованного пользователя")
    public AdsDto getAdsByUser() {
        return adsSevice.getAdsByUser();
    }

    @PatchMapping("{id}/image")
//    @Operation(summary = "Обновление картинки объявления")
    public String updateImage(@PathVariable Integer id, String imageLink) {
        return adsSevice.updateImage(id, imageLink);
    }
}
