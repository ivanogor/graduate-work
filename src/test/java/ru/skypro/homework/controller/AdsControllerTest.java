package ru.skypro.homework.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.core.Authentication;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import ru.skypro.homework.dto.CreateOrUpdateAd;
import ru.skypro.homework.entity.AdEntity;
import ru.skypro.homework.entity.UserEntity;
import ru.skypro.homework.repository.AdRepository;
import ru.skypro.homework.service.impl.AdsServiceImpl;
import ru.skypro.homework.utils.AdServiceUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.BDDMockito.*;

@AutoConfigureMockMvc
@SpringBootTest
class AdsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AdRepository adRepository;

    @MockBean
    private AdServiceUtils adServiceUtils;

    @SpyBean
    private AdsServiceImpl adsService;

    private ObjectMapper mapper;

    @InjectMocks
    private AdsController adsController;

    @BeforeEach
    void setUp() {
        mapper = new ObjectMapper();
    }

    @Test
    @WithMockUser
    void createAdsTest() throws Exception {
        //given
        CreateOrUpdateAd createOrUpdateAd = CreateOrUpdateAd.builder()
                .title("test title")
                .description("test description").build();

        UserEntity user = UserEntity.builder()
                .id(1L)
                .email("email@email.ru")
                .phone("+777777")
                .username("username")
                .build();

        MockMultipartFile image = new MockMultipartFile("image", "test.jpg",
                "image/jpeg", "test image".getBytes());
        MockMultipartFile properties = new MockMultipartFile("properties", "",
                "application/json", mapper.writeValueAsBytes(createOrUpdateAd));
        //when
        when(adServiceUtils.handleUser(any(Authentication.class))).thenReturn(user);
        //then
        mockMvc.perform(MockMvcRequestBuilders.multipart("/ads")
                        .file(properties)
                        .file(image))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.author").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value("test title"));
    }

    @Test
    @WithMockUser
    void getExtendedAdTest() throws Exception {
        //given
        Integer adId = 1;

        AdEntity ad = AdEntity.builder()
                .pk(1)
                .author(1L)
                .title("Test Ad")
                .description("Test Description").build();

        UserEntity user = UserEntity.builder()
                .id(1L)
                .email("email@email.ru")
                .phone("+777777")
                .username("username")
                .build();
        //when
        when(adServiceUtils.handleUser(any(Authentication.class))).thenReturn(user);
        when(adRepository.findById(any(Integer.class))).thenReturn(Optional.of(ad));
        //then
        mockMvc.perform(MockMvcRequestBuilders.get("/ads/{id}", adId))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value("Test Ad"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description")
                        .value("Test Description"));
    }

    @Test
    void getAdsDtoTest() throws Exception {
        //given
        AdEntity ad1 = AdEntity.builder()
                .pk(1)
                .author(1L)
                .title("Test Ad 1")
                .description("Test Description 1").build();

        AdEntity ad2 = AdEntity.builder()
                .pk(2)
                .author(1L)
                .title("Test Ad 2")
                .description("Test Description 2").build();

        ArrayList<AdEntity> ads = new ArrayList<>(List.of(ad1, ad2));
        //when
        when(adRepository.findAll()).thenReturn(ads);
        //then
        mockMvc.perform(MockMvcRequestBuilders.get("/ads"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.count").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$.results[0].title")
                        .value("Test Ad 1"));
    }

    @Test
    @WithMockUser
    void deleteAdTest() throws Exception {
        //given
        Integer adId = 1;

        UserEntity user = UserEntity.builder()
                .id(1L)
                .email("email@email.ru")
                .phone("+777777")
                .username("username")
                .build();

        AdEntity ad = AdEntity.builder()
                .pk(1)
                .author(1L)
                .title("Test Ad")
                .description("Test Description").build();
        //when
        when(adRepository.findById(any(Integer.class))).thenReturn(Optional.of(ad));
        when(adServiceUtils.handleUser(any(Authentication.class))).thenReturn(user);
        //then
        mockMvc.perform(MockMvcRequestBuilders.delete("/ads/{id}", adId))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @WithMockUser
    void updateAd() throws Exception {
        //given
        Integer adId = 1;

        UserEntity user = UserEntity.builder()
                .id(1L)
                .email("email@email.ru")
                .phone("+777777")
                .username("username")
                .build();

        AdEntity ad = AdEntity.builder()
                .pk(1)
                .author(1L)
                .title("Test Ad")
                .description("Test Description").build();

        CreateOrUpdateAd createAd = CreateOrUpdateAd.builder()
                .description("Test Description")
                .price(100)
                .title("Test Ad").build();

        //when
        when(adRepository.findById(any(Integer.class))).thenReturn(Optional.of(ad));
        when(adRepository.save(any(AdEntity.class))).thenReturn(ad);
        when(adServiceUtils.handleUser(any(Authentication.class))).thenReturn(user);
        //then
        mockMvc.perform(MockMvcRequestBuilders.patch("/ads/{id}", adId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(createAd)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value("Test Ad"));
    }

    @Test
    @WithMockUser
    void getAdsByUser() throws Exception {
        //given
        UserEntity user = UserEntity.builder()
                .id(1L)
                .email("email@email.ru")
                .phone("+777777")
                .username("username")
                .build();

        AdEntity ad1 = AdEntity.builder()
                .pk(1)
                .author(1L)
                .title("Test Ad 1")
                .description("Test Description 1").build();

        AdEntity ad2 = AdEntity.builder()
                .pk(2)
                .author(1L)
                .title("Test Ad 2")
                .description("Test Description 2").build();

        ArrayList<AdEntity> ads = new ArrayList<>(List.of(ad1, ad2));
        //when
        when(adServiceUtils.handleUser(any(Authentication.class))).thenReturn(user);
        when(adRepository.findAllByAuthor(anyLong())).thenReturn(ads);
        //then
        mockMvc.perform(MockMvcRequestBuilders.get("/ads/me"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.count").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$.results[0].title")
                        .value("Test Ad 1"));
    }

    @Test
    @WithMockUser
    void updateImage() throws Exception {
        //given
        Integer adId = 1;

        UserEntity user = UserEntity.builder()
                .id(1L)
                .email("email@email.ru")
                .phone("+777777")
                .username("username")
                .build();

        AdEntity ad = AdEntity.builder()
                .pk(1)
                .author(1L)
                .title("Test Ad")
                .description("Test Description").build();

        MockMultipartFile image = new MockMultipartFile("image", "test.jpg",
                "image/jpeg", "test image".getBytes());
        //when
        when(adRepository.findById(any(Integer.class))).thenReturn(Optional.of(ad));
        when(adServiceUtils.handleUser(any(Authentication.class))).thenReturn(user);
        //then
        mockMvc.perform(MockMvcRequestBuilders.multipart("/ads/{id}/image", adId)
                        .file(image)
                        .with(request -> {
                            request.setMethod("PATCH");
                            return request;
                        }))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}