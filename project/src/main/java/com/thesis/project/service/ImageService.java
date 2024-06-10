package com.thesis.project.service;

import com.thesis.project.exception.ResourceNotFoundException;
import com.thesis.project.helper.ImageHelper;
import com.thesis.project.model.CarImage;
import com.thesis.project.model.CarListing;
import com.thesis.project.repository.CarListingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ImageService {

    private final CarListingRepository carListingRepository;

    private final ImageHelper imageHelper;

    public List<String> getImagesForCarListing(int carListingId) {
        CarListing carListing = carListingRepository.findById(carListingId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Car listing with id " + carListingId + " not found"));

        List<CarImage> carImages = carListing.getImages();

        return carImages.stream()
                .map(carImage -> imageHelper.getImage(carImage.getImageUrl()))
                .collect(Collectors.toList());
    }

    public String getFirstImageForCarListing(int carListingId) {
        CarListing carListing = carListingRepository.findById(carListingId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Car listing with id " + carListingId + " not found"));

        return imageHelper.getImage(carListing.getImages().get(0).getImageUrl());
    }
}

