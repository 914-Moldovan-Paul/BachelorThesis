package com.thesis.project.repository;

import com.thesis.project.model.CarListing;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarRepository extends JpaRepository<CarListing, Integer>, CustomCarRepository {

}