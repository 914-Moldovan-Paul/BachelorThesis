package com.thesis.project.model;

import lombok.Getter;

@Getter
public enum GearboxType {
    MANUAL("Manual"),
    AUTOMATIC("Automatic");

    private final String displayName;

    GearboxType(String displayName) {
        this.displayName = displayName;
    }
}