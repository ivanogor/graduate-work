package ru.skypro.homework.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

/**
 * DTO для представления списка объявлений.
 * Этот класс используется для передачи данных о списке объявлений между слоями приложения.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Ads {

    /**
     * Общее количество объявлений.
     */
    @Schema(description = "Общее количество объявлений")
    private Integer count;

    /**
     * Список объявлений.
     */
    private ArrayList<Ad> results;

    /**
     * Создает объект Ads с указанным количеством объявлений и списком объявлений.
     *
     * @param countAds Количество объявлений.
     * @param ads Список объявлений.
     * @return Объект Ads.
     */
    public Ads getAds(Integer countAds, ArrayList<Ad> ads) {
        return Ads.builder()
                .count(countAds)
                .results(ads)
                .build();
    }
}