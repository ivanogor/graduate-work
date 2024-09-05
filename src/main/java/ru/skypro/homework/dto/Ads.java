package ru.skypro.homework.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Ads {

    @Schema(description = "общее количество объявлений")
    private Integer count;
    private ArrayList<Ad> results;

    public Ads getAds(Integer countAds, ArrayList<Ad> ads) {
        return Ads.builder()
                .count(countAds)
                .results(ads)
                .build();
    }
}
