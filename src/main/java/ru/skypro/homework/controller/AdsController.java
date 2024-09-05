package ru.skypro.homework.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.Ad;
import ru.skypro.homework.dto.Ads;
import ru.skypro.homework.dto.CreateOrUpdateAd;
import ru.skypro.homework.dto.ExtendedAd;
import ru.skypro.homework.service.impl.AdsServiceImpl;

import java.io.IOException;

import static org.springframework.http.MediaType.APPLICATION_OCTET_STREAM_VALUE;
import static org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE;

@Slf4j
@RestController
@CrossOrigin(value = "http://localhost:3000")
@RequestMapping("ads")
@Tag(name = "Объявления")
public class AdsController {
    private final AdsServiceImpl adsService;

    public AdsController(AdsServiceImpl adsService) {
        this.adsService = adsService;
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
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Ad> createAds(@RequestPart("properties") CreateOrUpdateAd createAd,
                                        @RequestPart("image") MultipartFile image,
                                        Authentication authentication) throws IOException {
        Ad response = adsService.createAds(createAd, image, authentication);
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
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ExtendedAd> getExtendedAd(@PathVariable Integer id,
                                                    Authentication authentication) {
        if (!adsService.findById(id)) {
            return ResponseEntity.notFound().build();
        }
        ExtendedAd response = adsService.getExtendedAd(id, authentication);
        return ResponseEntity.ok().body(response);
    }

    @Operation(summary = "Получение всех объявлений")
    @GetMapping
    @ApiResponses(value = {@ApiResponse(responseCode = "200",
            description = "Ok",
            content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = Ads.class))})
    })
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Ads> getAdsDto() {
        Ads response = adsService.getAdsDto();
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
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> deleteAd(@PathVariable Integer id,
                                      Authentication authentication) {
        if (!adsService.findById(id)) {
            return ResponseEntity.notFound().build();
        }
        adsService.deleteAd(id, authentication);
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
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Ad> updateAd(@PathVariable Integer id,
                                       @RequestBody CreateOrUpdateAd ad,
                                       Authentication authentication) {
        if (!adsService.findById(id)) {
            return ResponseEntity.notFound().build();
        }
        Ad response = adsService.updateAd(id, ad, authentication);
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
    public ResponseEntity<Ads> getAdsByUser(Authentication authentication) {
        Ads response = adsService.getAdsByUser(authentication);
        return ResponseEntity.ok().body(response);
    }

    @Operation(summary = "Обновление картинки объявления")
    @ApiResponses(value = {@ApiResponse(responseCode = "200",
            description = "Ok",
            content = @Content(mediaType = APPLICATION_OCTET_STREAM_VALUE,
                    array = @ArraySchema(schema = @Schema(type = "string",
                            format = "byte")))),
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
    @PreAuthorize("isAuthenticated()")
    @PatchMapping(value = "{id}/image", consumes = MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<byte[]> updateImage(@PathVariable Integer id,
                                              @RequestParam("image") MultipartFile image,
                                              Authentication authentication) throws IOException {
        if (!adsService.findById(id)) {
            return ResponseEntity.notFound().build();
        }
        byte[] response = adsService.updateImage(id, image, authentication);
        return ResponseEntity.ok().body(response);
    }
}