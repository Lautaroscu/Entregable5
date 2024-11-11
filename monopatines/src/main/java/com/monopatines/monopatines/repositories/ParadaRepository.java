package com.monopatines.monopatines.repositories;

import com.monopatines.monopatines.entities.Parada;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParadaRepository extends MongoRepository<Parada, String> {
}