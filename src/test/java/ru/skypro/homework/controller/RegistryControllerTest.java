package ru.skypro.homework.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.skypro.homework.dto.Register;
import ru.skypro.homework.entity.Role;
import ru.skypro.homework.repository.UserEntityRepository;
import ru.skypro.homework.service.impl.AuthServiceImpl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RegistryControllerTest {
    @Autowired
    private TestRestTemplate testRestTemplate;
    @Autowired
    private UserEntityRepository userEntityRepository;
    @Autowired
    private AuthServiceImpl authService;
    @LocalServerPort
    private int port;

    @Test
    void succsessReg(){
        Register register = Register.builder()
                .username("username")
                .password("password")
                .role(Role.USER)
                .build();

        ResponseEntity<?> newReg = testRestTemplate.postForEntity(
                "http://localhost:"+port+"/register",
                register,
                Boolean.class
        );
        assertThat(newReg.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        userEntityRepository.delete(userEntityRepository.findByUsername(register.getUsername()));
    }

    @Test
    void badreqReg(){
        boolean result = registerUser();
        assertTrue(result);

        Register register = Register.builder()
                .username("username")
                .password("password")
                .role(Role.USER)
                .build();

        ResponseEntity<?> newReg = testRestTemplate.postForEntity(
                "http://localhost:"+port+"/register",
                register,
                Boolean.class
        );
        assertThat(newReg.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        userEntityRepository.delete(userEntityRepository.findByUsername(register.getUsername()));
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
