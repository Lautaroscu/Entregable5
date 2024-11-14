package com.monopatines.monopatines.controllers;

import com.monopatines.monopatines.DTO.Scooter.ScooterFiltersDTO;
import com.monopatines.monopatines.DTO.Scooter.ScooterInputDTO;
import com.monopatines.monopatines.DTO.Scooter.ScooterOutputDTO;
import com.monopatines.monopatines.DTO.Scooter.ScooterStatusDTO;
import com.monopatines.monopatines.exceptions.BadRequestException;
import com.monopatines.monopatines.exceptions.ScooterNotFound;
import com.monopatines.monopatines.services.ParadaService;
import com.monopatines.monopatines.services.ScooterService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "ScooterController", description = "Controlador de operaciones sobre los monopatines")
@RestController
@RequestMapping("/api/scooters")
public class ScooterController {
    private final ScooterService scooterService;
    private final ParadaService paradaService;

    @Autowired
    public ScooterController(ScooterService scooterService , ParadaService paradaService) {
        this.scooterService = scooterService;
        this.paradaService = paradaService;
    }

    @Operation(
            summary = "Crear un nuevo monopatín",
            description = "Este endpoint permite crear un nuevo monopatín. Se debe proporcionar toda la información requerida.",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Monopatín creado exitosamente"),
                    @ApiResponse(responseCode = "400", description = "Solicitud incorrecta")
            }
    )
    @PostMapping
    public ResponseEntity<?> createScooter(@RequestBody ScooterInputDTO scooterInputDTO) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(scooterService.createScooter(scooterInputDTO));
        } catch (BadRequestException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @Operation(
            summary = "Obtener todos los monopatines con filtros opcionales",
            description = "Este endpoint devuelve una lista de monopatines registrados. Puedes filtrar por ubicación, estado y modelo, y ordenar los resultados.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Lista de monopatines"),
                    @ApiResponse(responseCode = "500", description = "Error interno del servidor")
            }
    )
    @GetMapping
    public ResponseEntity<List<ScooterOutputDTO>> getAllScooters(
            @RequestParam(value = "location", required = false) String location,
            @RequestParam(value = "status", required = false) String status,
            @RequestParam(value = "model", required = false) String model
    ) {
        ScooterFiltersDTO filtersDTO = new ScooterFiltersDTO(location, status, model);
        return ResponseEntity.status(HttpStatus.OK).body(scooterService.getAllScooters(filtersDTO));
    }

    @Operation(
            summary = "Obtener monopatín por ID",
            description = "Este endpoint devuelve los detalles de un monopatín especificado por su ID.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Monopatín encontrado"),
                    @ApiResponse(responseCode = "404", description = "Monopatín no encontrado")
            }
    )
    @GetMapping("/{id}")
    public ResponseEntity<?> getScooterById(@PathVariable String id) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(scooterService.getScooterById(id));
        } catch (ScooterNotFound e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @Operation(
            summary = "Actualizar un monopatín",
            description = "Este endpoint permite actualizar los detalles de un monopatín utilizando su ID.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Monopatín actualizado exitosamente"),
                    @ApiResponse(responseCode = "404", description = "Monopatín no encontrado"),
                    @ApiResponse(responseCode = "400", description = "Solicitud incorrecta")
            }
    )
    @PutMapping("/{id}")
    public ResponseEntity<?> updateScooter(@RequestBody ScooterInputDTO scooterInputDTO, @PathVariable String id) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(scooterService.updateScooter(scooterInputDTO, id));

        } catch (ScooterNotFound e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (BadRequestException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @Operation(
            summary = "Actualizar un monopatín",
            description = "Este endpoint permite eliminar un monopatín utilizando su ID.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Monopatín eliminado exitosamente"),
                    @ApiResponse(responseCode = "404", description = "Monopatín no encontrado"),
                    @ApiResponse(responseCode = "400", description = "Solicitud incorrecta")
            }
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteScooter(@PathVariable String id) {
        try {
            scooterService.deleteScooter(id);
            return ResponseEntity.ok().build();
        } catch (ScooterNotFound e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @Operation(
            summary = "Actualizar estado de un monopatín",
            description = "Este endpoint permite actualizar el estado de un monopatín utilizando su ID.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Estado del monopatín actualizado"),
                    @ApiResponse(responseCode = "404", description = "Monopatín no encontrado")
            }
    )
    @PatchMapping("/{id}")
    public ResponseEntity<?> setStatus(@PathVariable String id, @RequestBody ScooterStatusDTO scooterStatusDTO) {
        try {
            scooterService.setStatus(scooterStatusDTO, id);
            return ResponseEntity.ok().build();
        } catch (ScooterNotFound e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
    //obtiene todos los monopatines cercanos a un usuario
    @GetMapping("/nearby")
    public ResponseEntity<List<ScooterOutputDTO>> getScootersNearForUser(
            @RequestParam double lat ,
            @RequestParam double lng
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(paradaService.getScootersNearUserLocation(lat , lng));
    }
}
