package com.tarifas.tarifas.repositories;

import com.tarifas.tarifas.entities.Tarifa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TarifasRepository extends JpaRepository<Tarifa, Long> {
    Tarifa findByTipoTarifa(String tipoTarifa);
}