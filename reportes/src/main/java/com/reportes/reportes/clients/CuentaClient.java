package com.reportes.reportes.clients;

import com.reportes.reportes.clients.models.CuentaDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PatchMapping;

@FeignClient(name = "cuentas", url = "http://localhost:8081/api/accounts")
public interface CuentaClient {

    @PatchMapping("/management")
    CuentaDTO disableAccount();
}
