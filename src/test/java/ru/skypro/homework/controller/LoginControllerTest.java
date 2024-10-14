package ru.skypro.homework.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.skypro.homework.dto.Login;
import ru.skypro.homework.dto.Register;
import ru.skypro.homework.entity.Role;
import ru.skypro.homework.repository.UserEntityRepository;
import ru.skypro.homework.service.impl.AuthServiceImpl;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class LoginControllerTest {
    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private AuthServiceImpl authService;

    @Autowired
    private UserEntityRepository userEntityRepository;

    @LocalServerPort
    private int port;

    @Test
    public void loginSuccess(){
        boolean result = registerUser();
        assertTrue(result);

        Login login = Login.builder()
                .username("username")
                .password("password")
                .build();

        ResponseEntity<?> newLogin = testRestTemplate.postForEntity(
                "http://localhost:"+port+"/login",
                login,
                Boolean.class
        );
        assertThat(newLogin.getStatusCode()).isEqualTo(HttpStatus.OK);
        userEntityRepository.delete(userEntityRepository.findByUsername(login.getUsername()));
    }

    @Test
    public void loginUnauthorized(){
        boolean result = registerUser();
        assertTrue(result);

        Login login = Login.builder()
                .username("username")
                .password("password2")
                .build();

        ResponseEntity<?> newLogin = testRestTemplate.postForEntity(
                "http://localhost:"+port+"/login",
                login,
                Boolean.class
        );
        assertThat(newLogin.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
        userEntityRepository.delete(userEntityRepository.findByUsername(login.getUsername()));
    }

    public Boolean registerUser() {
        Register register = Register.builder()
                .username("username")
                .password("password")
                .role(Role.USER)
                .build();
        return authService.register(register);
    }
}