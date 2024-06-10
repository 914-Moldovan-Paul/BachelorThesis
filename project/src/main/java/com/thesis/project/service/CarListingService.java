package com.thesis.project.service;

import com.thesis.project.dto.CarListingDto;
import com.thesis.project.dto.QueryDto;
import com.thesis.project.dto.SmallCarListingDto;
import com.thesis.project.exception.ResourceNotFoundException;
import com.thesis.project.helper.ImageHelper;
import com.thesis.project.mapper.CarListingMapper;
import com.thesis.project.model.CarImage;
import com.thesis.project.model.CarListing;
import com.thesis.project.repository.CarImageRepository;
import com.thesis.project.repository.CarListingRepository;
import com.thesis.project.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CarListingService {

    private final CarListingRepository carListingRepository;
    private final UserRepository userRepository;
    private final CarImageRepository carImageRepository;

    private final ImageHelper imageHelper;
    private Map<String, Integer> carCount;

    @PostConstruct
    private void init() {
        carCount = getCarCount();
    }

    @Transactional
    public CarListingDto saveCar(CarListingDto carListingDto, int userId, MultipartFile[] images) {
        CarListing carListing = CarListingMapper.INSTANCE.carListingDtoToCarListing(carListingDto);

        carListing.setUser(userRepository.findById(userId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User with id " + userId + " not found")));

        saveImages(images, carListing);

        String key = carListing.getBrand() + carListing.getModel();
        carCount.merge(key, 1, Integer::sum);

        return CarListingMapper.INSTANCE.carListingToCarListingDto(carListingRepository.save(carListing));
    }

    public CarListingDto getCarById(int id) {
        return CarListingMapper.INSTANCE.carListingToCarListingDto(carListingRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Car listing with id " + id + " not found")));
    }

    public List<SmallCarListingDto> getAllCarListings() {
        return carListingRepository.findAll()
                .stream()
                .map(CarListingMapper.INSTANCE::carListingToSmallCarListingDto)
                .collect(Collectors.toList());
    }

    public List<SmallCarListingDto> getCarListingsForQuery(QueryDto queryDto) {
        return carListingRepository.executeCustomQuery(queryDto.getQuery())
                .stream()
                .map(CarListingMapper.INSTANCE::carListingToSmallCarListingDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public CarListingDto updateCarListing(int carListingId, CarListingDto updatedCarListingDto, MultipartFile[] images) {
        CarListing originalCarListing = carListingRepository.findById(carListingId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Car listing with id " + carListingId + " not found"));

        CarListing updatedCarListing = CarListingMapper.INSTANCE.carListingDtoToCarListing(updatedCarListingDto);
        updatedCarListing.setId(carListingId);
        updatedCarListing.setUser(originalCarListing.getUser());

        List<CarImage> originalCarImages = originalCarListing.getImages();
        for (CarImage carImage : originalCarImages) {
            imageHelper.deleteImage(carImage.getImageUrl());
        }
        saveImages(images, updatedCarListing);

        return CarListingMapper.INSTANCE.carListingToCarListingDto(carListingRepository.save(updatedCarListing));
    }

    private void saveImages(MultipartFile[] images, CarListing carListing) {
        List<CarImage> carImages = new ArrayList<>();
        for (MultipartFile image : images) {
            String imageUrl = imageHelper.saveImage(image);
            CarImage carImage = new CarImage();
            carImage.setImageUrl(imageUrl);
            carImages.add(carImage);
        }

        carImageRepository.saveAll(carImages);
        carListing.setImages(carImages);
    }

    public void deleteCarListing(int id) {
        CarListing carListing = carListingRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Car listing with id " + id + " not found"));

        for (CarImage carImage : carListing.getImages()) {
            imageHelper.deleteImage(carImage.getImageUrl());
        }

        carListingRepository.deleteById(id);
    }

    public Map<String, Integer> getCarCount() {
        List<Object[]> results = carListingRepository.countByBrandAndModel();
        Map<String, Integer> carCount = new HashMap<>();

        for (Object[] result : results) {
            String brand = (String) result[0];
            String model = (String) result[1];
            Long count = (Long) result[2];
            String key = brand + " " + model;
            carCount.put(key, count.intValue());
        }

        return carCount;
    }

}
