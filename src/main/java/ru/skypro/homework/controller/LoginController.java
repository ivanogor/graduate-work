package ru.skypro.homework.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.skypro.homework.dto.Login;
import ru.skypro.homework.service.AuthService;

/**
 * Контроллер для обработки запросов на авторизацию пользователей.
 */
@Slf4j
@CrossOrigin(value = "http://localhost:3000")
@RestController
@RequiredArgsConstructor
@Tag(name = "Авторизация")
public class LoginController {
    private final AuthService authService;

    /**
     * Обрабатывает запрос на авторизацию пользователя.
     *
     * @param login Данные для авторизации (логин и пароль).
     * @return Ответ об успешной авторизации или ошибке.
     */
    @PostMapping("/login")
    @Operation(summary = "Авторизация пользователя")
    @ApiResponses(value = {@ApiResponse(responseCode = "201",
            description = "OK",
            content = {@Content(
                    schema = @Schema(hidden = true))}),
            @ApiResponse(responseCode = "401",
                    description = "Unauthorized",
                    content = @Content(schema = @Schema(hidden = true)))
    })
    public ResponseEntity<?> login(@RequestBody(required = false) Login login) {
        log.info("login: {}", login);
        if (authService.login(login.getUsername(), login.getPassword())) {
            return ResponseEntity.ok().build();
        } else {
            log.error("login UNAUTHORIZED");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
}