package com.monopatines.monopatines.repositories;

import com.monopatines.monopatines.entities.Parada;
import com.monopatines.monopatines.entities.Scooter;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ParadaRepository extends MongoRepository<Parada, String> {
    @Query("{ 'latitudParada': ?0, 'longitudParada': ?1 }")
    List<Parada> findByLocation(double lat, double lon);}