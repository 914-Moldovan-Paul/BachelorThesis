package com.thesis.project.service;

import com.thesis.project.exception.ResourceNotFoundException;
import com.thesis.project.helper.ImageHelper;
import com.thesis.project.model.CarImage;
import com.thesis.project.model.CarListing;
import com.thesis.project.repository.CarListingRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ImageServiceTest {

    @Mock
    private CarListingRepository carListingRepository;

    @Mock
    private ImageHelper imageHelper;

    @InjectMocks
    private ImageService imageService;

    private CarListing carListing;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        CarImage carImage1 = new CarImage();
        carImage1.setImageUrl("image1.jpg");

        CarImage carImage2 = new CarImage();
        carImage2.setImageUrl("image2.jpg");

        carListing = new CarListing();
        carListing.setId(1);
        carListing.setImages(Arrays.asList(carImage1, carImage2));
    }

    @Test
    public void testGetImagesForCarListing() {
        when(carListingRepository.findById(1)).thenReturn(Optional.of(carListing));
        when(imageHelper.getImage("image1.jpg")).thenReturn("image1.jpg");
        when(imageHelper.getImage("image2.jpg")).thenReturn("image2.jpg");

        List<String> images = imageService.getImagesForCarListing(1);

        assertNotNull(images);
        assertEquals(2, images.size());
        assertEquals("image1.jpg", images.get(0));
        assertEquals("image2.jpg", images.get(1));

        verify(carListingRepository, times(1)).findById(1);
        verify(imageHelper, times(1)).getImage("image1.jpg");
        verify(imageHelper, times(1)).getImage("image2.jpg");
    }

    @Test
    public void testGetImagesForCarListing_NotFound() {
        when(carListingRepository.findById(1)).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () ->
                imageService.getImagesForCarListing(1));

        assertEquals("Car listing with id 1 not found", exception.getMessage());

        verify(carListingRepository, times(1)).findById(1);
        verify(imageHelper, never()).getImage(anyString());
    }

    @Test
    public void testGetFirstImageForCarListing() {
        when(carListingRepository.findById(1)).thenReturn(Optional.of(carListing));
        when(imageHelper.getImage("image1.jpg")).thenReturn("image1.jpg");

        String image = imageService.getFirstImageForCarListing(1);

        assertNotNull(image);
        assertEquals("image1.jpg", image);

        verify(carListingRepository, times(1)).findById(1);
        verify(imageHelper, times(1)).getImage("image1.jpg");
    }

    @Test
    public void testGetFirstImageForCarListing_NotFound() {
        when(carListingRepository.findById(1)).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () ->
                imageService.getFirstImageForCarListing(1));

        assertEquals("Car listing with id 1 not found", exception.getMessage());

        verify(carListingRepository, times(1)).findById(1);
        verify(imageHelper, never()).getImage(anyString());
    }
}
