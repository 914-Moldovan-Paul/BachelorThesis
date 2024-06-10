package com.thesis.project.helper;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;

@Component
public class ImageHelper {

    @Value("${file.upload-dir}")
    private String UPLOAD_DIR;

    public String saveImage(MultipartFile image) {
        Path uploadPath = Paths.get(UPLOAD_DIR);
        if (!Files.exists(uploadPath)) {
            try {
                Files.createDirectories(uploadPath);
            } catch (IOException e) {
                throw new RuntimeException("Could not create upload directory!", e);
            }
        }

        String fileName = System.currentTimeMillis() + "_" + image.getOriginalFilename();
        Path filePath = uploadPath.resolve(fileName);

        try {
            Files.copy(image.getInputStream(), filePath);
        } catch (IOException e) {
            throw new RuntimeException("Could not save image file: " + fileName, e);
        }

        return filePath.toString();
    }

    public String getImage(String filePath) {
        try {
            return Base64.getEncoder().encodeToString(Files.readAllBytes(Paths.get(filePath)));
        } catch (IOException e) {
            throw new RuntimeException("Could not get image file from: " + filePath, e);
        }
    }

    public void deleteImage(String filePath) {
        try {
            Files.deleteIfExists(Paths.get(filePath));
        } catch (IOException e) {
            throw new RuntimeException("Could not delete image file from: " + filePath, e);
        }
    }
}
