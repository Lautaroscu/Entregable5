package com.viajes.viajes.clients;

import com.viajes.viajes.clients.models.ScooterDTO;
import com.viajes.viajes.clients.models.ScooterStatusDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "scooter-service", url = "http://localhost:8082/api/scooters")
public interface ScooterClient {
    @GetMapping("/{id}")
    ScooterDTO getScooterById(@PathVariable String id);

    @PatchMapping("/{id}")
    void setStatus(@PathVariable String id, @RequestBody ScooterStatusDTO scooterDTO);
}
