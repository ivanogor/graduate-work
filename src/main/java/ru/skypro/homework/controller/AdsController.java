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
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.Ad;
import ru.skypro.homework.dto.Ads;
import ru.skypro.homework.dto.CreateOrUpdateAd;
import ru.skypro.homework.dto.ExtendedAd;
import ru.skypro.homework.entity.Role;
import ru.skypro.homework.entity.UserEntity;
import ru.skypro.homework.service.impl.AdsServiceImpl;
import ru.skypro.homework.utils.AdServiceUtils;

import java.io.IOException;

import static org.springframework.http.MediaType.APPLICATION_OCTET_STREAM_VALUE;
import static org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE;

/**
 * Контроллер для управления объявлениями.
 * Этот контроллер предоставляет API для создания, получения, обновления и удаления объявлений.
 * Он также обеспечивает доступ к объявлениям авторизованного пользователя и обновление изображений объявлений.
 */
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Slf4j
@RestController
@CrossOrigin(value = "http://localhost:3000")
@RequestMapping("ads")
@Tag(name = "Объявления")
public class AdsController {
    private final AdsServiceImpl adsService;
    private final AdServiceUtils adUtils;

    /**
     * Конструктор для создания экземпляра AdsController.
     *
     * @param adsService Сервис для работы с объявлениями.
     * @param utils Утилиты для работы с объявлениями.
     */
    public AdsController(AdsServiceImpl adsService, AdServiceUtils utils) {
        this.adsService = adsService;
        this.adUtils = utils;
    }

    /**
     * Добавление нового объявления.
     *
     * @param createAd Данные для создания объявления.
     * @param image Изображение для объявления.
     * @param authentication Аутентификация пользователя.
     * @return Объект ResponseEntity с созданным объявлением.
     * @throws IOException Если возникает ошибка при обработке изображения.
     */
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

    /**
     * Получение информации об объявлении по его ID.
     *
     * @param id ID объявления.
     * @param authentication Аутентификация пользователя.
     * @return Объект ResponseEntity с расширенной информацией об объявлении.
     */
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
    public ResponseEntity<ExtendedAd> getExtendedAd(@PathVariable Integer id,
                                                    Authentication authentication) {
        if (!adsService.foundById(id)) {
            return ResponseEntity.notFound().build();
        } else {
            ExtendedAd response = adsService.getExtendedAd(id, authentication);
            return ResponseEntity.ok().body(response);
        }
    }

    /**
     * Получение всех объявлений.
     *
     * @return Объект ResponseEntity со списком всех объявлений.
     */
    @Operation(summary = "Получение всех объявлений")
    @GetMapping
    @ApiResponses(value = {@ApiResponse(responseCode = "200",
            description = "Ok",
            content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = Ads.class))})
    })
    public ResponseEntity<Ads> getAdsDto() {
        Ads response = adsService.getAdsDto();
        return ResponseEntity.ok().body(response);
    }

    /**
     * Удаление объявления по его ID.
     *
     * @param id ID объявления.
     * @param authentication Аутентификация пользователя.
     * @return Объект ResponseEntity с соответствующим статусом.
     */
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
        if (!adsService.foundById(id)) {
            return ResponseEntity.notFound().build();
        } else if (!checkAccess(authentication, id) && !getRole(authentication).equals(Role.ADMIN)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        } else {
            adsService.deleteAd(id, authentication);
            return ResponseEntity.ok().build();
        }
    }

    /**
     * Обновление информации об объявлении по его ID.
     *
     * @param id ID объявления.
     * @param ad Данные для обновления объявления.
     * @param authentication Аутентификация пользователя.
     * @return Объект ResponseEntity с обновленным объявлением.
     */
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
        if (!adsService.foundById(id)) {
            return ResponseEntity.notFound().build();
        } else if (!checkAccess(authentication, id) && !getRole(authentication).equals(Role.ADMIN)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        } else {
            Ad response = adsService.updateAd(id, ad, authentication);
            return ResponseEntity.ok().body(response);
        }
    }

    /**
     * Получение объявлений авторизованного пользователя.
     *
     * @param authentication Аутентификация пользователя.
     * @return Объект ResponseEntity со списком объявлений пользователя.
     */
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

    /**
     * Обновление картинки объявления по его ID.
     *
     * @param id ID объявления.
     * @param image Новое изображение для объявления.
     * @param authentication Аутентификация пользователя.
     * @return Объект ResponseEntity с обновленным изображением.
     * @throws IOException Если возникает ошибка при обработке изображения.
     */
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
    public ResponseEntity<?> updateImage(@PathVariable Integer id,
                                              @RequestParam("image") MultipartFile image,
                                              Authentication authentication) throws IOException {
        if (!adsService.foundById(id)) {
            return ResponseEntity.notFound().build();
        } else if (!checkAccess(authentication, id) && !getRole(authentication).equals(Role.ADMIN)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        } else {
            adsService.updateImage(id, image, authentication);
            return ResponseEntity.ok().build();
        }
    }

    private boolean checkAccess(Authentication authentication, Integer adId) {
        Long userId = adUtils.handleUser(authentication).getId();
        Long authorId = adsService.findById(adId).getAuthor();
        return userId.equals(authorId);
    }

    private Role getRole(Authentication authentication) {
        UserEntity user = adUtils.handleUser(authentication);
        return user.getRole();
    }
}