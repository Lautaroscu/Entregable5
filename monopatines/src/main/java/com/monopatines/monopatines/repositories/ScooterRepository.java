package com.monopatines.monopatines.repositories;

import com.monopatines.monopatines.entities.Scooter;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ScooterRepository extends MongoRepository<Scooter, String> {
}
