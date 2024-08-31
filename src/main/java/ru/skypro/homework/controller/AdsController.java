package ru.skypro.homework.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.Ad;
import ru.skypro.homework.dto.Ads;
import ru.skypro.homework.dto.CreateOrUpdateAd;
import ru.skypro.homework.dto.ExtendedAd;
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
    @ApiResponses(value = {@ApiResponse(responseCode = "201",
            description = "Created",
            content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = Ad.class))}),
            @ApiResponse(responseCode = "401",
                    description = "Unauthorized",
                    content = @Content(schema = @Schema(hidden = true)))
    })
    public ResponseEntity<Ad> createAds(@RequestPart("properties") Ad ad,
                                        @RequestPart("image") MultipartFile image) {
        Ad response = adsSevice.createNewAd(ad);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(summary = "Получение информации об объявлении")
    @GetMapping("{id}")
    @ApiResponses(value = {@ApiResponse(responseCode = "200",
            description = "Ok",
            content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = ExtendedAd.class))}),
            @ApiResponse(responseCode = "401",
                    description = "Unauthorized",
                    content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(responseCode = "404",
                    description = "Not found",
                    content = @Content(schema = @Schema(hidden = true)))
    })
    public ResponseEntity<ExtendedAd> getExtendedAd(@PathVariable Integer id) {
        ExtendedAd response = adsSevice.getExtendedAd(id);
        if (!adsSevice.findById(id)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(response);
    }

    @Operation(summary = "Получение всех объявлений")
    @GetMapping
    @ApiResponses(value = {@ApiResponse(responseCode = "200",
            description = "Ok",
            content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = Ads.class))})
    })
    public ResponseEntity<Ads> getAdsDto() {
        Ads response = adsSevice.getAdsDto();
        return ResponseEntity.ok().body(response);
    }

    @Operation(summary = "Удаление объявления")
    @DeleteMapping("{id}")
    @ApiResponses(value = {@ApiResponse(responseCode = "204",
            description = "No Content",
            content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(responseCode = "401",
                    description = "Unauthorized",
                    content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(responseCode = "403",
                    description = "Forbidden",
                    content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(responseCode = "404",
                    description = "Not found",
                    content = @Content(schema = @Schema(hidden = true)))
    })
    public ResponseEntity<?> deleteAd(@PathVariable Integer id) {
        if (!adsSevice.findById(id)) {
            return ResponseEntity.notFound().build();
        }
        adsSevice.deleteAd(id);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Обновление информации об объявлении")
    @PatchMapping("{id}")
    @ApiResponses(value = {@ApiResponse(responseCode = "200",
            description = "Ok",
            content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = Ad.class))}),
            @ApiResponse(responseCode = "401",
                    description = "Unauthorized",
                    content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(responseCode = "403",
                    description = "Forbidden",
                    content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(responseCode = "404",
                    description = "Not found",
                    content = @Content(schema = @Schema(hidden = true)))
    })
    public ResponseEntity<Ad> updateAd(@PathVariable Integer id, @RequestBody CreateOrUpdateAd ad) {
        if (!adsSevice.findById(id)) {
            return ResponseEntity.notFound().build();
        }
        Ad response = adsSevice.updateAd(id, ad);
        return ResponseEntity.ok().body(response);
    }

    @Operation(summary = "Получение объявлений авторизованного пользователя")
    @GetMapping("/me")
    @ApiResponses(value = {@ApiResponse(responseCode = "200",
            description = "Ok",
            content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = Ads.class))}),
            @ApiResponse(responseCode = "401",
                    description = "Unauthorized",
                    content = @Content(schema = @Schema(hidden = true)))
    })
    public ResponseEntity<Ads> getAdsByUser() {
        Ads response = adsSevice.getAdsByUser();
        return ResponseEntity.ok().body(response);
    }

    @Operation(summary = "Обновление картинки объявления")
    @PatchMapping(value = "{id}/image", consumes = MULTIPART_FORM_DATA_VALUE)
    @ApiResponses(value = {@ApiResponse(responseCode = "200",
            description = "Ok",
            content = {@Content(mediaType = "application/octet-stream",
                    schema = @Schema())}),
            @ApiResponse(responseCode = "401",
                    description = "Unauthorized",
                    content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(responseCode = "403",
                    description = "Forbidden",
                    content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(responseCode = "404",
                    description = "Not found",
                    content = @Content(schema = @Schema(hidden = true)))
    })
    public ResponseEntity<?> updateImage(@PathVariable Integer id,
                                         @RequestParam("image") MultipartFile image) {
        if (!adsSevice.findById(id)) {
            return ResponseEntity.notFound().build();
        }
        String response = adsSevice.updateImage(id, image);
        return ResponseEntity.ok().body(response);
    }
}