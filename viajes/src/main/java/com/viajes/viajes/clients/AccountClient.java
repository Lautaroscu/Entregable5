package com.viajes.viajes.clients;

import com.viajes.viajes.clients.models.Account;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "cuentas", url = "http://localhost:8081")
public interface AccountClient {
    @GetMapping("/api/accounts/{id}")
    Account getAccountById(@PathVariable("id") Long id);


    @PatchMapping("/updateSaldo/{id}")
    Account updateSaldo(@PathVariable("id") Long id, @RequestBody double saldo);
}
