package com.viajes.viajes.repositories;

import com.viajes.viajes.entities.Trip;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TripRepository extends MongoRepository<Trip, String> {

    // Consulta para buscar viajes por el nombre del conductor
    @Query("{ 'scooterDTO.id': ?0 }")
    List<Trip> findByScooterID(String id);
}