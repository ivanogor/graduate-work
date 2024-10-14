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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import ru.skypro.homework.dto.NewPassword;
import ru.skypro.homework.dto.UpdateUser;
import ru.skypro.homework.entity.UserEntity;
import ru.skypro.homework.repository.UserEntityRepository;
import ru.skypro.homework.service.impl.UserServiceImpl;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserEntityRepository userRepository;

    @MockBean
    PasswordEncoder passwordEncoder;

    @SpyBean
    private UserServiceImpl userService;

    @InjectMocks
    private UserController controller;

    private ObjectMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = new ObjectMapper();
    }

    @Test
    @WithMockUser
    void setPasswordTest() throws Exception {
        //given
        UserEntity user = UserEntity.builder()
                .id(1L)
                .email("email@email.ru")
                .phone("+777777")
                .username("username")
                .enabled(true)
                .password("test current")
                .build();

        NewPassword newPassword = NewPassword.builder()
                .currentPassword("test current")
                .newPassword("test new").build();
        //when
        when(userRepository.findByUsername(anyString())).thenReturn(user);
        when(passwordEncoder.matches(anyString(), anyString())).thenReturn(false);
        //then
        mockMvc.perform(MockMvcRequestBuilders.post("/users/set_password")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(newPassword)))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @WithMockUser
    void getCurrentUserTest() throws Exception {
        //given
        UserEntity user = UserEntity.builder()
                .id(1L)
                .email("email@email.ru")
                .phone("+777777")
                .username("username")
                .enabled(true)
                .build();
        //when
        when(userRepository.findByUsername(anyString())).thenReturn(user);
        //then
        mockMvc.perform(MockMvcRequestBuilders.get("/users/me"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value("email@email.ru"));
    }

    @Test
    @WithMockUser
    void updateUserTest() throws Exception {
        //given
        UserEntity user = UserEntity.builder()
                .id(1L)
                .email("email@email.ru")
                .phone("+777777")
                .username("username")
                .build();

        UpdateUser updateUser = UpdateUser.builder()
                .firstName("Test first")
                .lastName("test last").build();
        //when
        when(userRepository.findByUsername(anyString())).thenReturn(user);
        //then
        mockMvc.perform(MockMvcRequestBuilders.patch("/users/me")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(updateUser)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value("Test first"));
    }

    @Test
    @WithMockUser
    void updateUserAvatarTest() throws Exception {
        //given
        UserEntity user = UserEntity.builder()
                .id(1L)
                .email("email@email.ru")
                .phone("+777777")
                .username("username")
                .build();
        MockMultipartFile image = new MockMultipartFile("image", "test.jpg",
                "image/jpeg", "test image".getBytes());
        //when
        when(userRepository.findByUsername(anyString())).thenReturn(user);
        //then
        mockMvc.perform(MockMvcRequestBuilders.multipart("/users/me/image")
                        .file(image)
                        .with(request -> {
                            request.setMethod("PATCH");
                            return request;
                        }))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}