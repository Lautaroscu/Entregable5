package com.cuentas.cuentas.controladores;


import com.cuentas.cuentas.DTO.*;

import com.cuentas.cuentas.excepciones.AccountNotFoundException;
import com.cuentas.cuentas.excepciones.BadRequestException;
import com.cuentas.cuentas.servicios.ServicioCuenta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/accounts")
public class CuentaControlador {
    private final ServicioCuenta servicioCuenta;

    @Autowired
    public CuentaControlador(ServicioCuenta servicioCuenta) {
        this.servicioCuenta = servicioCuenta;
    }
    @GetMapping
    public ResponseEntity<List<OutputCuentaDTO>> getCuentas() {
        return ResponseEntity.status(HttpStatus.OK).body(servicioCuenta.getCuentas());
    }

    @PostMapping
    public ResponseEntity<?> createAccount(@RequestBody InputCuentaDTO inputCuentaDTO) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(servicioCuenta.crearCuenta(inputCuentaDTO));
        } catch (BadRequestException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(servicioCuenta.getAccountById(id));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
    @PutMapping("/{id}")
    public ResponseEntity<?> updateAccount(@PathVariable Long id, @RequestBody InputCuentaUpdateDTO inputCuentaDTO) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(servicioCuenta.updateAccount(id , inputCuentaDTO));
        }catch (AccountNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }catch (BadRequestException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PatchMapping("/management")
    public ResponseEntity<?> manageAccount(@RequestBody AccountAvailabilityDTO accountAvailabilityDTO) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(servicioCuenta.manageAvailability(accountAvailabilityDTO));
        } catch (AccountNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }

    }

    @PatchMapping("/update-balance/{id}")
    public ResponseEntity<?> updateSaldo(@PathVariable Long id, @RequestBody SaldoAccountDTO saldo) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(servicioCuenta.setSaldo(id, saldo));
        } catch (AccountNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAccount(@PathVariable Long id) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(servicioCuenta.deleteAccount(id));
        }catch (AccountNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
