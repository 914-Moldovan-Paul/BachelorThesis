package com.thesis.project.controller;

import com.thesis.project.model.CarListing;
import com.thesis.project.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@AllArgsConstructor
@RestController
@RequestMapping("/users")
public class UserController {

    private UserService userService;

//    @GetMapping
//    public ResponseEntity<List<CarListing>> getCarListingsForUser(@PathVariable("id") int userId) {
//        return ResponseEntity.ok(userService.getCarListingsForUser(userId));
//    }


}
