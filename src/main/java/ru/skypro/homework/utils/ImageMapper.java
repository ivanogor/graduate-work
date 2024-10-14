package ru.skypro.homework.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

import static java.nio.file.StandardOpenOption.CREATE_NEW;

/**
 * Сервис для работы с изображениями.
 * Этот класс предоставляет методы для сохранения изображений на диск и получения их по ссылке.
 */
@Service
@Transactional
public class ImageMapper {
    private Logger logger = LoggerFactory.getLogger(ImageMapper.class);

    /**
     * Путь к директории для сохранения изображений.
     */
    private final String savePath = "D:/Image";

    /**
     * Сохраняет изображение на диск и возвращает путь к нему.
     *
     * @param imageFile Изображение для сохранения.
     * @param uniqueId Уникальный идентификатор для имени файла.
     * @return Путь к сохраненному изображению.
     * @throws IOException Если возникает ошибка при сохранении файла.
     */
    public String mapFileToPath(MultipartFile imageFile, String uniqueId) throws IOException {
        logger.info("Was invoked handle image to string path method");
        Path filePath = Path.of(savePath, uniqueId + "." +
                getExtention(imageFile));
        Files.createDirectories(filePath.getParent());
        Files.deleteIfExists(filePath);
        try (
                InputStream is = imageFile.getInputStream();
                OutputStream os = Files.newOutputStream(filePath, CREATE_NEW);
                BufferedInputStream bis = new BufferedInputStream(is, 1024);
                BufferedOutputStream bos = new BufferedOutputStream(os, 1024);
        ) {
            bis.transferTo(bos);
        }
        return "/image/" + uniqueId + "." + getExtention(imageFile);
    }

    /**
     * Возвращает изображение по его ссылке.
     *
     * @param imageLink Ссылка на изображение.
     * @return Массив байтов изображения.
     * @throws IOException Если возникает ошибка при чтении файла.
     */
    public byte[] mapPathToFile(String imageLink) throws IOException {
        Path path = Path.of(imageLink);
        File imageFile = new File(savePath.concat("\\").concat(imageLink));
        try (
                FileInputStream fis = new FileInputStream(imageFile);
        ) {
            byte[] bytes = new byte[(int) imageFile.length()];
            fis.read(bytes);
            return bytes;
        }
    }

    /**
     * Возвращает расширение файла изображения.
     *
     * @param imageFile Изображение.
     * @return Расширение файла изображения.
     */
    private String getExtention(MultipartFile imageFile) {
        String fileName = imageFile.getOriginalFilename();
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }

    /**
     * Возвращает полный путь к изображению по его аргументу.
     *
     * @param arg Аргумент, содержащий имя файла изображения.
     * @return Полный путь к изображению.
     */
    public String getPath(String arg) {
        return savePath.concat("\\").concat(arg);
    }
}