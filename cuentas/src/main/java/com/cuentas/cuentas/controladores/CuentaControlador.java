package com.cuentas.cuentas.controladores;

import com.cuentas.cuentas.DTO.InputCuentaDTO;
import com.cuentas.cuentas.excepciones.BadRequestException;
import com.cuentas.cuentas.servicios.ServicioCuenta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/accounts")
public class CuentaControlador {
    private final ServicioCuenta servicioCuenta;
    @Autowired
    public CuentaControlador(ServicioCuenta servicioCuenta){
        this.servicioCuenta = servicioCuenta;
    }
    @PostMapping
    public ResponseEntity<?> createAccount(@RequestBody InputCuentaDTO inputCuentaDTO){
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(servicioCuenta.crearCuenta(inputCuentaDTO));
        }catch (BadRequestException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id){
        try {
            return ResponseEntity.status(HttpStatus.OK).body(servicioCuenta.getAccountById(id));
        }catch (Exception e){
            return ResponseEntity.notFound().build();
        }

    }
}
