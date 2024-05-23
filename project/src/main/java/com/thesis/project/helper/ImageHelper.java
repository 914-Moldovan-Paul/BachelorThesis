package com.thesis.project.helper;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ImageHelper {

    //TODO
    private static String UPLOAD_DIR = "C:/THESIS/imagestorage";

    public static String saveImage(MultipartFile image) {
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
}
