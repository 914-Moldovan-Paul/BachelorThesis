package com.thesis.project.service;

import com.thesis.project.dto.CarListingDto;
import com.thesis.project.dto.SmallCarListingDto;
import com.thesis.project.exception.ResourceNotFoundException;
import com.thesis.project.helper.ImageHelper;
import com.thesis.project.mapper.CarListingMapper;
import com.thesis.project.model.CarImage;
import com.thesis.project.model.CarListing;
import com.thesis.project.repository.CarImageRepository;
import com.thesis.project.repository.CarRepository;
import com.thesis.project.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CarService {


    private final CarRepository carRepository;
    private final UserRepository userRepository;
    private final CarImageRepository carImageRepository;

    public CarListingDto saveCar(CarListingDto carListingDto, int userId, MultipartFile[] images) {
        CarListing carListing = CarListingMapper.INSTANCE.carListingDtoToCarListing(carListingDto);

        carListing.setUser(userRepository.findById(userId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User with id " + userId + " not found")));

        List<CarImage> carImages = new ArrayList<>();
        for(MultipartFile image : images) {
            String imageUrl = ImageHelper.saveImage(image);
            CarImage carImage = new CarImage();
            carImage.setImageUrl(imageUrl);
            carImages.add(carImage);
        }
        carImageRepository.saveAll(carImages);
        carListing.setImages(carImages);

        return CarListingMapper.INSTANCE.carListingToCarListingDto(carRepository.save(carListing));
    }

    public CarListing getCarById(int id) {
        return carRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Car listing with id " + id + " not found"));
    }

    public List<SmallCarListingDto> getAllCarListings() {
        return carRepository.findAll()
                .stream()
                .map(CarListingMapper.INSTANCE::carListingToSmallCarListingDto)
                .collect(Collectors.toList());
    }

    public List<SmallCarListingDto> getCarListingsForQuery(String query) {
        return carRepository.executeCustomQuery(query)
                .stream()
                .map(CarListingMapper.INSTANCE::carListingToSmallCarListingDto)
                .collect(Collectors.toList());
    }

    public CarListing updateCarListing(int id, CarListing updatedCarListing) {
        CarListing carListing = carRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Car listing with id " + id + " not found"));

        carListing.setBrand(updatedCarListing.getBrand());
        carListing.setModel(updatedCarListing.getModel());

        return carRepository.save(carListing);
    }

    public void deleteCarListing(int id) {
        carRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Car listing with id " + id + " not found"));

        carRepository.deleteById(id);
    }
}
