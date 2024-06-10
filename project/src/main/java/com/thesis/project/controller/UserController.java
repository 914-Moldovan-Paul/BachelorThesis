package com.thesis.project.controller;

import com.thesis.project.dto.SmallCarListingDto;
import com.thesis.project.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/users")
public class UserController {

    private UserService userService;

    @GetMapping("/{userId}/cars")
    public ResponseEntity<List<SmallCarListingDto>> getAllCarListingsForUser(@PathVariable("userId") int userId) {
        return ResponseEntity.ok(userService.getAllCarListingsForUser(userId));
    }

}
