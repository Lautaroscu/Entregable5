package com.autenticacion.autenticacion.clients;

import com.autenticacion.autenticacion.DTO.RegisterInputDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "cuentas", url = "http://localhost:8081")
public interface AccountClient {
    @GetMapping("api/accounts/ownerEmail/{ownerEmail}")
    AccountModel getByOwnerEmail(@PathVariable("ownerEmail") String ownerEmail);

    @GetMapping("/api/accounts/emailAvailable")
    Boolean emailAvailable(@RequestParam String email);


    @PostMapping("/api/accounts")
    AccountModel createAccount(@RequestBody RegisterInputDTO account);

}
