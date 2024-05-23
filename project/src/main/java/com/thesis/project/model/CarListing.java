package com.thesis.project.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "car_listings")
@Getter
@Setter
public class CarListing {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String brand;
    private String model;
    private int year;
    private String generation;
    private String vehicleIdentificationNumber;
    private int kilometers;
    private int horsepower;
    private int engineCapacity;
    private String transmission;
    private String gearboxType;
    private String fuelType;
    private String color;

    private String title;
    private String description;
    private int price;
    private String city;
    private String phoneNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(fetch = FetchType.LAZY)
    private List<CarImage> images;

}
