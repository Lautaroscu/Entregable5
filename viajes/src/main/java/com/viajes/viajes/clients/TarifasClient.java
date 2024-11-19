package com.viajes.viajes.clients;

import com.viajes.viajes.clients.models.TarifaDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "tarifas", url = "http://localhost:8088")
public interface TarifasClient {
    @GetMapping("api/tarifas")
    TarifaDTO getTarifaByTipo(@RequestParam("tipo") String tipo);
}
