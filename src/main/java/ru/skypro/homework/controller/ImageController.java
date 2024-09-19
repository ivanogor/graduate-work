package ru.skypro.homework.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.skypro.homework.utils.ImageMapper;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Контроллер для работы с изображениями.
 * Этот контроллер предоставляет API для получения изображений из файловой системы.
 */
@RestController
@Tag(name = "API для работы с изображениями")
@CrossOrigin("http://localhost:3000")
@RequestMapping("image")
@RequiredArgsConstructor
public class ImageController {
    private final ImageMapper mapper;

    /**
     * Получение изображения по его аргументу.
     *
     * @param arg Аргумент, используемый для определения пути к изображению.
     * @param response Объект HttpServletResponse для отправки изображения клиенту.
     * @throws IOException Если возникает ошибка при чтении или записи изображения.
     */
    @GetMapping("{arg}")
    @Operation(summary = "Получение изображения из файла")
    public void getImage(@PathVariable String arg, HttpServletResponse response) throws IOException {
        Path pathFile = Path.of(mapper.getPath(arg));
        File imageFile = new File(mapper.getPath(arg));
        try (
                InputStream is = Files.newInputStream(pathFile);
                OutputStream os = response.getOutputStream();
        ) {
            response.setStatus(200);
            response.setContentType("image/jpeg");
            response.setContentLength((int) imageFile.length());
            is.transferTo(os);
        }
    }
}