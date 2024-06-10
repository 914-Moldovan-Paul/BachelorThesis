package com.thesis.project.controller;

import com.thesis.project.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/images")
public class ImageController {

    private final ImageService imageService;

    @GetMapping("{id}")
    public ResponseEntity<List<String>> getImagesForCarListing(@PathVariable("id") int carListingId) {
        return ResponseEntity.ok(imageService.getImagesForCarListing(carListingId));
    }

    @GetMapping("/get-first/{id}")
    public ResponseEntity<String> getFirstImageForCarListing(@PathVariable("id") int carListingId) {
        return ResponseEntity.ok(imageService.getFirstImageForCarListing(carListingId));
    }

}



