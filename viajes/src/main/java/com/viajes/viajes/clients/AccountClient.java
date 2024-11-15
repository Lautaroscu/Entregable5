package com.viajes.viajes.clients;

import com.viajes.viajes.clients.models.Account;
import com.viajes.viajes.clients.models.InputCuentaUpdateDTO;
import com.viajes.viajes.clients.models.SaldoAccountDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "cuentas", url = "http://localhost:8081")
public interface AccountClient {
    @GetMapping("/api/accounts/{id}")
    Account getAccountById(@PathVariable("id") Long id);


    @PutMapping("/api/accounts/{id}")
    Account updateAccount(@PathVariable("id") Long id, @RequestBody InputCuentaUpdateDTO account);
}