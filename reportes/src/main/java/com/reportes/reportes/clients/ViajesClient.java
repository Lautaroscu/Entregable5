package com.reportes.reportes.clients;

import com.reportes.reportes.clients.models.TripDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "viajes", url = "http://localhost:8081")
public interface ViajesClient {

    @GetMapping("/api/viajes/scooter/{id}")
    List<TripDTO> getViajesByScooterId(@PathVariable("id") Long id);
}

