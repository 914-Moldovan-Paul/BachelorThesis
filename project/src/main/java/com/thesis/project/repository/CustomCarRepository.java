package com.thesis.project.repository;

import com.thesis.project.model.CarListing;

import java.util.List;

public interface CustomCarRepository {

    List<CarListing> executeCustomQuery(String query);
}
