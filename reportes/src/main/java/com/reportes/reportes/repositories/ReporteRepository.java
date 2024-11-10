package com.reportes.reportes.repositories;

import com.reportes.reportes.entities.Tarifa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ReporteRepository extends JpaRepository<Tarifa, Long> {

//    @Query(value = "SELECT t FROM Tarifa t WHERE t.id = :id;", nativeQuery = false)
//    public Tarifa findById(@Param("id") Long id);
}
