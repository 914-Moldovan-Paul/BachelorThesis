package com.thesis.project.model;

import lombok.Getter;

@Getter
public enum FuelType {
    GASOLINE("Gasoline"),
    DIESEL("Diesel"),
    ELECTRIC("Electric"),
    HYBRID("Hybrid");

    private final String displayName;

    FuelType(String displayName) {
        this.displayName = displayName;
    }
}