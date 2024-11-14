package com.viajes.viajes.clients;

import com.viajes.viajes.clients.models.Account;
import com.viajes.viajes.clients.models.TarifaDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "reportes", url = "http://localhost:8080")
public interface ReportClient {
    @GetMapping("api/reportes/tarifas")
    TarifaDTO getTarifaByTipo(@RequestParam("tipo") String tipo);
}
