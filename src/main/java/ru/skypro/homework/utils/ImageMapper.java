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

@Service
@Transactional
public class ImageMapper {
    private Logger logger = LoggerFactory.getLogger(ImageMapper.class);

    //@Value("${path.to.ads.folders}")
    private final String savePath = "C:/Image";

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

    private String getExtention(MultipartFile imageFile) {
        String fileName = imageFile.getOriginalFilename();
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }

    public String getPath(String arg) {
        return savePath.concat("\\").concat(arg);
    }
}
