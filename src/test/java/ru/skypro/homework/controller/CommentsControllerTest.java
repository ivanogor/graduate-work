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
import org.springframework.security.core.Authentication;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import ru.skypro.homework.dto.CreateOrUpdateComment;
import ru.skypro.homework.entity.CommentEntity;
import ru.skypro.homework.entity.UserEntity;
import ru.skypro.homework.repository.CommentRepository;
import ru.skypro.homework.repository.UserEntityRepository;
import ru.skypro.homework.service.impl.CommentsServiceImpl;
import ru.skypro.homework.utils.AdServiceUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@AutoConfigureMockMvc
@SpringBootTest
class CommentsControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CommentRepository commentRepository;

    @MockBean
    private UserEntityRepository userRepository;

    @MockBean
    private AdServiceUtils adServiceUtils;

    @SpyBean
    private CommentsServiceImpl commentsService;

    @InjectMocks
    private CommentsController controller;

    private ObjectMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = new ObjectMapper();
    }

    @Test
    @WithMockUser
    void getAllTest() throws Exception {
        //given
        Long id = 1L;
        UserEntity user = UserEntity.builder()
                .id(1L)
                .email("email@email.ru")
                .phone("+777777")
                .username("username")
                .build();

        CommentEntity comment1 = CommentEntity.builder()
                .pk(1)
                .author(1L)
                .text("text 1")
                .ad(1L).build();

        CommentEntity comment2 = CommentEntity.builder()
                .pk(2)
                .author(1L)
                .text("text 2")
                .ad(1L).build();

        ArrayList<CommentEntity> comments = new ArrayList<>(List.of(comment1, comment2));
        //when
        when(commentRepository.findAllByAd(anyLong())).thenReturn(comments);
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
        //then
        mockMvc.perform(MockMvcRequestBuilders.get("/ads/{id}/comments", id))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.count").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$.results[0].text")
                        .value("text 1"));
    }

    @Test
    @WithMockUser
    void addCommentTest() throws Exception {
        //given
        Long id = 1L;
        CreateOrUpdateComment createOrUpdateComment = CreateOrUpdateComment.builder()
                .text("test text").build();

        UserEntity user = UserEntity.builder()
                .id(1L)
                .email("email@email.ru")
                .phone("+777777")
                .username("username")
                .build();

        //when
        when(adServiceUtils.handleUser(any(Authentication.class))).thenReturn(user);
        //then
        mockMvc.perform(MockMvcRequestBuilders.post("/ads/{id}/comments", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(createOrUpdateComment)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.text").value("test text"));
    }

    @Test
    @WithMockUser
    void delCommentTest() throws Exception {
        //given
        Integer adId = 1;
        Integer commentId = 1;

        UserEntity user = UserEntity.builder()
                .id(1L)
                .email("email@email.ru")
                .phone("+777777")
                .username("username")
                .build();

        CommentEntity comment = CommentEntity.builder()
                .pk(1)
                .author(1L)
                .text("test text")
                .ad(1L).build();
        //when
        when(commentRepository.findById(any(Integer.class))).thenReturn(Optional.of(comment));
        when(adServiceUtils.handleUser(any(Authentication.class))).thenReturn(user);
        //then
        mockMvc.perform(MockMvcRequestBuilders.delete("/ads/{adId}/comments/{commentId}", adId, commentId))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @WithMockUser
    void updateCommentTest() throws Exception {
        //given
        Integer adId = 1;
        Integer commentId = 1;

        UserEntity user = UserEntity.builder()
                .id(1L)
                .email("email@email.ru")
                .phone("+777777")
                .username("username")
                .build();

        CommentEntity comment = CommentEntity.builder()
                .pk(1)
                .author(1L)
                .text("test text")
                .ad(1L).build();

        CreateOrUpdateComment createOrUpdateComment = CreateOrUpdateComment.builder()
                .text("test text").build();

        //when
        when(commentRepository.findById(any(Integer.class))).thenReturn(Optional.of(comment));
        when(adServiceUtils.handleUser(any(Authentication.class))).thenReturn(user);
        //then
        mockMvc.perform(MockMvcRequestBuilders.patch("/ads/{adId}/comments/{commentId}", adId, commentId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(createOrUpdateComment)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.text").value("test text"));
    }
}