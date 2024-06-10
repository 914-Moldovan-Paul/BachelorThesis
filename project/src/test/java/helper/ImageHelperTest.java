package com.thesis.project.helper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

public class ImageHelperTest {

    private ImageHelper imageHelper;

    @BeforeEach
    public void setUp() {
        imageHelper = new ImageHelper();
        try {
            var field = ImageHelper.class.getDeclaredField("UPLOAD_DIR");
            field.setAccessible(true);
            String uploadDir = "test";
            field.set(imageHelper, uploadDir);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testSaveImage() throws IOException {
        Path tempDir = Files.createTempDirectory("upload-dir");
        Path tempFile = Files.createTempFile(tempDir, "test_image", ".jpg");
        Files.write(tempFile, "test image content".getBytes());
        byte[] fileContent = Files.readAllBytes(tempFile);
        MockMultipartFile mockMultipartFile = new MockMultipartFile(
                "file",
                "test_image.jpg",
                "image/jpeg",
                fileContent
        );

        String savedFilePath = imageHelper.saveImage(mockMultipartFile);

        assertTrue(Files.exists(Paths.get(savedFilePath)));

        Files.deleteIfExists(Paths.get(savedFilePath));
        Files.deleteIfExists(tempFile);
        Files.deleteIfExists(tempDir);
    }


    @Test
    public void testGetImage() throws IOException {
        Path tempDir = Files.createTempDirectory("upload-dir");
        Path tempFile = Files.createTempFile(tempDir, "test_image", ".jpg");
        Files.write(tempFile, "test image content".getBytes());

        String base64Image = imageHelper.getImage(tempFile.toString());

        assertNotNull(base64Image);
        assertFalse(base64Image.isEmpty());

        Files.deleteIfExists(tempFile);
        Files.deleteIfExists(tempDir);
    }

    @Test
    public void testDeleteImage() throws IOException {
        Path tempDir = Files.createTempDirectory("upload-dir");
        Path tempFile = Files.createTempFile(tempDir, "test_image", ".jpg");
        Files.write(tempFile, "test image content".getBytes());

        imageHelper.deleteImage(tempFile.toString());

        assertFalse(Files.exists(tempFile));

        Files.deleteIfExists(tempDir);
    }
}
