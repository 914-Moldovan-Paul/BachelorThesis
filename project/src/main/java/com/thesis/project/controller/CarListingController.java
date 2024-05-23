package com.thesis.project.controller;


import com.thesis.project.dto.CarListingDto;
import com.thesis.project.dto.SmallCarListingDto;
import com.thesis.project.model.CarListing;
import com.thesis.project.service.CarService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@CrossOrigin
@AllArgsConstructor
@RestController
@RequestMapping("/cars")
public class CarListingController {

    private CarService carService;

    @PostMapping
    public ResponseEntity<CarListingDto> createCarListing(@RequestPart CarListingDto carListingDto, @RequestParam int userId, @RequestParam MultipartFile[] images) {
        return ResponseEntity.status(HttpStatus.CREATED).body(carService.saveCar(carListingDto, userId, images));
    }

    @GetMapping("{id}")
    public ResponseEntity<CarListing> getCarById(@PathVariable("id") int carId) {
        return ResponseEntity.ok(carService.getCarById(carId));
    }

    @GetMapping
    public ResponseEntity<List<SmallCarListingDto>> getAllCarListings() {
        return ResponseEntity.ok(carService.getAllCarListings());
    }

    @GetMapping("/by-query")
    public ResponseEntity<List<SmallCarListingDto>> getCarListingsForQuery(@RequestParam String query) {
        return ResponseEntity.ok(carService.getCarListingsForQuery(query));
    }

    @PutMapping("{id}")
    public ResponseEntity<CarListing> updateCarListing(@PathVariable("id") int carId, @RequestBody CarListing updatedCarListing) {
        return ResponseEntity.ok(carService.updateCarListing(carId, updatedCarListing));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteCarListing(@PathVariable("id") int carId) {
        carService.deleteCarListing(carId);
        return ResponseEntity.ok("Car listing with id " + carId + " deleted successfully");
    }
}
