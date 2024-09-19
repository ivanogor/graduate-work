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
import org.springframework.web.bind.annotation.*;
import ru.skypro.homework.dto.Register;
import ru.skypro.homework.service.AuthService;

/**
 * Контроллер для регистрации пользователей.
 * Этот контроллер предоставляет API для регистрации новых пользователей в системе.
 */
@Slf4j
@CrossOrigin(value = "http://localhost:3000")
@RestController
@RequestMapping("register")
@Tag(name = "Регистрация")
@RequiredArgsConstructor
public class RegistryController {
    private final AuthService authService;

    /**
     * Регистрация нового пользователя.
     *
     * @param register Объект Register, содержащий данные для регистрации.
     * @return Объект ResponseEntity с соответствующим статусом.
     */
    @PostMapping
    @Operation(summary = "Регистрация пользователя")
    @ApiResponses(value = {@ApiResponse(responseCode = "201",
            description = "Created",
            content = {@Content(
                    schema = @Schema(hidden = true))}),
            @ApiResponse(responseCode = "400",
                    description = "Bad Request",
                    content = @Content(schema = @Schema(hidden = true)))
    })
    public ResponseEntity<?> register(@RequestBody Register register) {
        log.info("register : {}", register);
        if (authService.register(register)) {
            log.info("register created");
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } else {
            log.info("register bad request");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}