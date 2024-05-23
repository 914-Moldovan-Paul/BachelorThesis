package com.thesis.project.repository;

import com.thesis.project.model.CarListing;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import java.util.List;

public class CustomCarRepositoryImpl implements CustomCarRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<CarListing> executeCustomQuery(String queryStr) {
        Query query = entityManager.createQuery(queryStr, CarListing.class);
        return query.getResultList();
    }
}
