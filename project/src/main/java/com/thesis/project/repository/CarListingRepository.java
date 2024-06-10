package com.thesis.project.repository;

import com.thesis.project.model.CarListing;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CarListingRepository extends JpaRepository<CarListing, Integer>, CustomCarRepository {

    @Query("SELECT cl.brand, cl.model, COUNT(cl) FROM CarListing cl GROUP BY cl.brand, cl.model")
    List<Object[]> countByBrandAndModel();

}