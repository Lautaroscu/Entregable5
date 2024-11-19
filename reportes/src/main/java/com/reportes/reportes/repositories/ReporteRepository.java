package com.reportes.reportes.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReporteRepository extends JpaRepository<Tarifa, Long> {
    Tarifa findByTipoTarifa(String tipoTarifa);
}
