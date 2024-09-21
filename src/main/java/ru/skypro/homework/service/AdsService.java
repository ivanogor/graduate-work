package ru.skypro.homework.service;

import org.springframework.security.core.Authentication;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.Ad;
import ru.skypro.homework.dto.Ads;
import ru.skypro.homework.dto.CreateOrUpdateAd;
import ru.skypro.homework.dto.ExtendedAd;
import ru.skypro.homework.entity.AdEntity;

import java.io.IOException;

/**
 * Интерфейс для работы с объявлениями.
 */
public interface AdsService {

    /**
     * Создает новое объявление.
     *
     * @param createAd Данные для создания объявления.
     * @param image Изображение для объявления.
     * @param authentication Аутентификация пользователя.
     * @return Созданное объявление.
     * @throws IOException Если возникает ошибка при обработке изображения.
     */
    Ad createAds(CreateOrUpdateAd createAd, MultipartFile image, Authentication authentication)
            throws IOException;

    /**
     * Получает расширенную информацию об объявлении по его ID.
     *
     * @param id ID объявления.
     * @param authentication Аутентификация пользователя.
     * @return Расширенная информация об объявлении.
     */
    ExtendedAd getExtendedAd(Integer id, Authentication authentication);

    /**
     * Получает все объявления.
     *
     * @return Список всех объявлений.
     */
    Ads getAdsDto();

    /**
     * Удаляет объявление по его ID.
     *
     * @param id ID объявления.
     * @param authentication Аутентификация пользователя.
     */
    void deleteAd(Integer id, Authentication authentication);

    /**
     * Обновляет информацию об объявлении по его ID.
     *
     * @param id ID объявления.
     * @param adDto Данные для обновления объявления.
     * @param authentication Аутентификация пользователя.
     * @return Обновленное объявление.
     */
    Ad updateAd(Integer id, CreateOrUpdateAd adDto, Authentication authentication);

    /**
     * Получает объявления, созданные авторизованным пользователем.
     *
     * @param authentication Аутентификация пользователя.
     * @return Список объявлений, созданных авторизованным пользователем.
     */
    Ads getAdsByUser(Authentication authentication);

    /**
     * Обновляет изображение объявления по его ID.
     *
     * @param id ID объявления.
     * @param image Новое изображение для объявления.
     * @param authentication Аутентификация пользователя.
     * @return Массив байтов нового изображения.
     * @throws IOException Если возникает ошибка при обработке изображения.
     */
    void updateImage(Integer id, MultipartFile image, Authentication authentication)
            throws IOException;

    /**
     * Проверяет, существует ли объявление с указанным ID.
     *
     * @param id ID объявления.
     * @return true, если объявление существует, иначе false.
     */
    Boolean foundById(Integer id);

    /**
     * Находит объявление по его ID.
     *
     * @param id ID объявления.
     * @return Сущность объявления, если найдена, иначе null.
     */
    AdEntity findById(Integer id);
}