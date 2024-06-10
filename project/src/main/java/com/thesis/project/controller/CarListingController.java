package com.thesis.project.controller;


import com.thesis.project.dto.CarListingDto;
import com.thesis.project.dto.QueryDto;
import com.thesis.project.dto.SmallCarListingDto;
import com.thesis.project.service.CarListingService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


@AllArgsConstructor
@RestController
@RequestMapping("/cars")
public class CarListingController {

    private CarListingService carListingService;

    @PostMapping
    public ResponseEntity<CarListingDto> createCarListing(@RequestPart CarListingDto carListingDto, @RequestParam int userId, @RequestParam MultipartFile[] images) {
        return ResponseEntity.status(HttpStatus.CREATED).body(carListingService.saveCar(carListingDto, userId, images));
    }

    @GetMapping("{id}")
    public ResponseEntity<CarListingDto> getCarById(@PathVariable("id") int carId) {
        return ResponseEntity.ok(carListingService.getCarById(carId));
    }

    @GetMapping
    public ResponseEntity<List<SmallCarListingDto>> getAllCarListings() {
        return ResponseEntity.ok(carListingService.getAllCarListings());
    }

    @PostMapping("/by-query")
    public ResponseEntity<List<SmallCarListingDto>> getCarListingsForQuery(@RequestBody QueryDto queryDto) {
        return ResponseEntity.ok(carListingService.getCarListingsForQuery(queryDto));
    }

    @PutMapping("{id}")
    public ResponseEntity<CarListingDto> updateCarListing(@PathVariable("id") int carId, @RequestPart CarListingDto carListingDto, @RequestParam MultipartFile[] images) {
        return ResponseEntity.ok(carListingService.updateCarListing(carId, carListingDto, images));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteCarListing(@PathVariable("id") int carId) {
        carListingService.deleteCarListing(carId);
        return ResponseEntity.ok("Car listing with id " + carId + " deleted successfully");
    }
}
