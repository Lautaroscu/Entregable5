package com.cuentas.cuentas.controladores;


import com.cuentas.cuentas.DTO.*;

import com.cuentas.cuentas.excepciones.AccountNotFoundException;
import com.cuentas.cuentas.excepciones.BadRequestException;
import com.cuentas.cuentas.servicios.ServicioCuenta;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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
    @Operation(
            summary = "Obtener todas las cuentas",
            description = "Este endpoint devuelve una lista de todas las cuentas registradas.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Lista de cuentas obtenida con éxito"),
                    @ApiResponse(responseCode = "500", description = "Error interno del servidor")
            }
    )
    public ResponseEntity<List<OutputCuentaDTO>> getCuentas() {
        return ResponseEntity.status(HttpStatus.OK).body(servicioCuenta.getCuentas());
    }

    @PostMapping
    @Operation(
            summary = "Crea una nueva cuenta",
            description = "Este endpoint permite crear una nueva cuenta asociada a un usuario. Se debe proporcionar toda la información requerida.",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Cuenta creada exitosamente"),
                    @ApiResponse(responseCode = "400", description = "Solicitud incorrecta")
            }
    )
    public ResponseEntity<OutputCuentaDTO> createAccount(@RequestBody InputCuentaDTO inputCuentaDTO) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(servicioCuenta.crearCuenta(inputCuentaDTO));
        } catch (BadRequestException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Obtener una cuenta por ID",
            description = "Este endpoint devuelve los detalles de una cuenta específica mediante su ID.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Cuenta encontrada exitosamente"),
                    @ApiResponse(responseCode = "404", description = "Cuenta no encontrada")
            }
    )
    public ResponseEntity<OutputCuentaDTO> getById(@PathVariable Long id) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(servicioCuenta.getAccountById(id));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
    @GetMapping("/ownerEmail/{ownerEmail}")
    @Operation(
            summary = "Obtener cuenta por email del propietario",
            description = "Este endpoint devuelve la cuenta asociada al email de un propietario específico.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Cuenta obtenidas con éxito"),
                    @ApiResponse(responseCode = "404", description = "No se encontraro cuenta para el email proporcionado")
            }
    )
    public ResponseEntity<OutputCuentaDTO> getByOwnerEmail(@PathVariable String ownerEmail) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(servicioCuenta.getByOwnerEmail(ownerEmail));
        }catch (AccountNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
    @GetMapping("/emailAvailable")
    @Operation(
            summary = "Verificar disponibilidad de email",
            description = "Este endpoint verifica si un email está disponible para asociarse a una nueva cuenta.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Email verificado con éxito"),
                    @ApiResponse(responseCode = "400", description = "Solicitud incorrecta")
            }
    )
    public ResponseEntity<Boolean> checkEmail(@RequestParam String email) {
        return ResponseEntity.status(HttpStatus.OK).body(servicioCuenta.avialableEmail(email));
    }
    @PutMapping("/{id}")
    @Operation(
            summary = "Actualizar una cuenta",
            description = "Este endpoint permite actualizar los detalles de una cuenta existente mediante su ID.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Cuenta actualizada exitosamente"),
                    @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos"),
                    @ApiResponse(responseCode = "404", description = "Cuenta no encontrada")
            }
    )
    public ResponseEntity<OutputCuentaDTO> updateAccount(@PathVariable Long id, @RequestBody InputCuentaUpdateDTO inputCuentaDTO) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(servicioCuenta.updateAccount(id , inputCuentaDTO));
        }catch (AccountNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }catch (BadRequestException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @PatchMapping("/management")
    @Operation(
            summary = "Gestionar disponibilidad de una cuenta",
            description = "Este endpoint permite gestionar la disponibilidad de una cuenta.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Disponibilidad gestionada exitosamente"),
                    @ApiResponse(responseCode = "404", description = "Cuenta no encontrada")
            }
    )
    public ResponseEntity<OutputCuentaDTO> manageAccount(@RequestBody AccountAvailabilityDTO accountAvailabilityDTO) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(servicioCuenta.manageAvailability(accountAvailabilityDTO));
        } catch (AccountNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

    }

    @PatchMapping("/update-balance/{id}")
    @Operation(
            summary = "Actualizar saldo de una cuenta",
            description = "Este endpoint permite actualizar el saldo de una cuenta específica.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Saldo actualizado exitosamente"),
                    @ApiResponse(responseCode = "404", description = "Cuenta no encontrada")
            }
    )
    public ResponseEntity<OutputCuentaDTO> updateSaldo(@PathVariable Long id, @RequestBody SaldoAccountDTO saldo) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(servicioCuenta.setSaldo(id, saldo));
        } catch (AccountNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
    @DeleteMapping("/{id}")
    @Operation(
            summary = "Eliminar una cuenta",
            description = "Este endpoint elimina una cuenta específica mediante su ID.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Cuenta eliminada exitosamente"),
                    @ApiResponse(responseCode = "404", description = "Cuenta no encontrada")
            }
    )
    public ResponseEntity<OutputCuentaDTO> deleteAccount(@PathVariable Long id) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(servicioCuenta.deleteAccount(id));
        }catch (AccountNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
}
