package com.reportes.reportes.clients;

import com.reportes.reportes.clients.models.ViajeDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "viajes", url = "http://localhost:8083/api/trips")
public interface ViajesClient {

    @GetMapping
    List<ViajeDTO> getViajes();

    @GetMapping("/scooter/{id}")
    List<ViajeDTO> getViajesByScooterId(@PathVariable("id") String id);
}

