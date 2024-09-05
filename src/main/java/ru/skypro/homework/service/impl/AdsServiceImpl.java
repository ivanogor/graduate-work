package ru.skypro.homework.service.impl;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.*;
import ru.skypro.homework.entity.AdEntity;

import ru.skypro.homework.entity.UserEntity;
import ru.skypro.homework.repository.AdRepository;
import ru.skypro.homework.repository.UserRepository;

import ru.skypro.homework.service.AdsService;
import ru.skypro.homework.utils.AdImageMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdsServiceImpl implements AdsService {

    private final AdRepository adRepository;
    private final UserRepository userRepository;

    private final Logger logger = LoggerFactory.getLogger(AdsServiceImpl.class);

    @Override
    public Ad createAds(CreateOrUpdateAd createAd, MultipartFile image, Authentication authentication)
            throws IOException {
        logger.info("Was invoked create Ad method");
        UserEntity user = handleUser(authentication);
        String uniqueId = user.getEmail().concat("-").concat(createAd.getTitle());
        String link = new AdImageMapper().mapFileToPath(image, uniqueId);
        AdEntity adEntity = createAd.mapDtoToAdEntity(link, user.getId());
        adRepository.save(adEntity);
        return Ad.mapEntityToDto(adEntity);

    }

    @Override
    public ExtendedAd getExtendedAd(Integer id, Authentication authentication) {
        logger.info("Was invoked get Ad info method");
        UserEntity user = handleUser(authentication);
        AdEntity adEntity = adRepository.findById(id).get();
        return ExtendedAd.mapAdEntityToDto(adEntity, user);
    }

    @Override
    public Ads getAdsDto() {
        logger.info("Was invoked get all Ads method");
        ArrayList<Ad> ads = adRepository.findAll()
                .stream()
                .map(e -> Ad.mapEntityToDto(e))
                .collect(Collectors.toCollection(ArrayList::new));
        Integer countAd = ads.size();
        return new Ads().getAds(countAd, ads);
    }

    @Override

    public void deleteAd(Integer id, Authentication authentication) {
        logger.info("Was invoked delete Ad method");
        adRepository.deleteById(id);

    }

    @Override
    public Ad updateAd(Integer id, CreateOrUpdateAd ad, Authentication authentication) {
        logger.info("Was invoked update Ad method");
        Ad updatedAd = ad.updateAd(adRepository.findById(id).get());
        adRepository.save(updatedAd.mapDtoToEntity(ad.getDescription()));
        return updatedAd;
    }

    @Override
    public Ads getAdsByUser(Authentication authentication) {
        logger.info("Was invoked get users Ads method");
        UserEntity user = handleUser(authentication);
        ArrayList<Ad> ads = adRepository.findAllByAuthor(user.getId()).stream()
                .map(e -> Ad.mapEntityToDto(e))
                .collect(Collectors.toCollection(ArrayList::new));
        Integer countAd = ads.size();
        return new Ads().getAds(countAd, ads);
    }

    @Override
    public byte[] updateImage(Integer id, MultipartFile image, Authentication authentication) {
        logger.info("Was invoked update Ads image method");
        return null;
    }

    @Override
    public Boolean findById(Integer id) {

        logger.info("Was invoked find Ad by id method");
        return !adRepository.findById(id).isEmpty();
    }

    @Override
    public UserEntity handleUser(Authentication authentication) {
        logger.info("Was invoked handle User method");
        UserDetails principal = (UserDetails) authentication.getPrincipal();
        return userRepository.findByUsername(principal.getUsername());

    }
}
