package ru.skypro.homework.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.mock.web.MockFilterChain;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.core.Authentication;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import ru.skypro.homework.dto.Ad;
import ru.skypro.homework.dto.CreateOrUpdateAd;
import ru.skypro.homework.entity.AdEntity;
import ru.skypro.homework.entity.UserEntity;
import ru.skypro.homework.repository.AdRepository;
import ru.skypro.homework.repository.UserRepository;
import ru.skypro.homework.service.impl.AdsServiceImpl;
import ru.skypro.homework.utils.AdServiceUtils;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@AutoConfigureMockMvc
@SpringBootTest
class AdsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AdRepository adRepository;

    @MockBean
    private UserRepository userRepository;

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
    void givenNewAd_whenCreateCommand_createAds() throws IOException, Exception {
        //given
        CreateOrUpdateAd createOrUpdateAd = CreateOrUpdateAd.builder()
                .title("test title")
                .description("test description").build();

        AdEntity ad = AdEntity.builder()
                .pk(1)
                .author(1L)
                .title("test title").build();

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
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.author").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value("test title"));
    }

    @Test
    void getExtendedAd() {
    }

    @Test
    void getAdsDto() {
    }

    @Test
    void deleteAd() {
    }

    @Test
    void updateAd() {
    }

    @Test
    void getAdsByUser() {
    }

    @Test
    void updateImage() {
    }
}