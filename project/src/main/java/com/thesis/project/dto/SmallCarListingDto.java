package com.thesis.project.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SmallCarListingDto {

    private String id;
    private String title;
    private String brand;
    private String model;
    private int kilometers;
    private int engineCapacity;
    private int horsepower;
    private String fuelType;
    private int year;
    private int price;
    private String city;

}
