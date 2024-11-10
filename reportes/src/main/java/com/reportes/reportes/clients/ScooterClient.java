package com.reportes.reportes.clients;

import com.reportes.reportes.clients.models.ScooterDTO;
import com.reportes.reportes.clients.models.ScooterStatus;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "scooter-service", url = "http://localhost:8082/api/scooters")
public interface ScooterClient {

    @GetMapping()
    List<ScooterDTO> getScooters();

    @GetMapping()
    List<ScooterDTO> getScooters(
            @RequestParam("status") ScooterStatus status
    );
}
