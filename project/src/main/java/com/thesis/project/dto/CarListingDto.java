package com.thesis.project.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CarListingDto {

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
    private String userDisplayName;

}
