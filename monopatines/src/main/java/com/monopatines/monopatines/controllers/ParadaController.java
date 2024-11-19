package com.monopatines.monopatines.controllers;

import com.monopatines.monopatines.DTO.Parada.ParadaInputDTO;
import com.monopatines.monopatines.DTO.Parada.ParadaOutputDTO;
import com.monopatines.monopatines.DTO.Parada.ScooterIDRequest;
import com.monopatines.monopatines.exceptions.ParadaNotFound;
import com.monopatines.monopatines.services.ParadaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/paradas")
public class ParadaController {
    private final ParadaService paradaService;

    @Autowired
    public ParadaController(ParadaService paradaService) {
        this.paradaService = paradaService;
    }

    // Crear una nueva parada
    @Operation(
            summary = "Crear una nueva parada",
            description = "Este endpoint permite crear una nueva parada. Se debe proporcionar toda la información requerida.",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Parada creada exitosamente"),
                    @ApiResponse(responseCode = "400", description = "Solicitud incorrecta")
            }
    )
    @PostMapping
    public ResponseEntity<ParadaOutputDTO> createParada(@RequestBody ParadaInputDTO paradaInputDTO) {
        ParadaOutputDTO paradaOutputDTO = paradaService.createParada(paradaInputDTO);
        return new ResponseEntity<>(paradaOutputDTO, HttpStatus.CREATED);
    }

    // Obtener todas las paradas
    @Operation(
            summary = "Obtener todas las paradas",
            description = "Este endpoint devuelve una lista de todas las paradas disponibles.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Lista de paradas obtenida exitosamente")
            }
    )
    @GetMapping
    public ResponseEntity<List<ParadaOutputDTO>> getAllParadas() {
        List<ParadaOutputDTO> paradas = paradaService.getAllParadas();
        return new ResponseEntity<>(paradas, HttpStatus.OK);
    }

    // Obtener una parada por ID
    @Operation(
            summary = "Obtener una parada por ID",
            description = "Este endpoint permite obtener los detalles de una parada específica mediante su ID.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Parada encontrada exitosamente"),
                    @ApiResponse(responseCode = "404", description = "Parada no encontrada")
            }
    )
    @GetMapping("/{id}")
    public ResponseEntity<?> getParadaById(@PathVariable String id) {
        try {
            ParadaOutputDTO paradaOutputDTO = paradaService.getParadaById(id);

            return new ResponseEntity<>(paradaOutputDTO, HttpStatus.OK);
        } catch (ParadaNotFound e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    // Actualizar el nombre de una parada
    @Operation(
            summary = "Actualizar el nombre de una parada",
            description = "Este endpoint permite actualizar el nombre de una parada existente utilizando su ID.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Parada actualizada exitosamente"),
                    @ApiResponse(responseCode = "404", description = "Parada no encontrada")
            }
    )
    @PutMapping("/{id}")
    public ResponseEntity<ParadaOutputDTO> updateParada(@PathVariable String id, @RequestBody ParadaInputDTO paradaInputDTO) {
        ParadaOutputDTO paradaOutputDTO = paradaService.updateParada(paradaInputDTO, id);
        return new ResponseEntity<>(paradaOutputDTO, HttpStatus.OK);
    }

    // Eliminar una parada
    @Operation(
            summary = "Eliminar una parada",
            description = "Este endpoint permite eliminar una parada específica utilizando su ID.",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Parada eliminada exitosamente"),
                    @ApiResponse(responseCode = "404", description = "Parada no encontrada")
            }
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteParada(@PathVariable String id) {
        paradaService.deleteParada(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    //Agrega monopatin de la parada
    @Operation(
            summary = "Agregar un monopatín a una parada",
            description = "Este endpoint permite agregar un monopatín existente a una parada específica utilizando el ID de la parada y el ID del monopatín.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Monopatín agregado exitosamente a la parada"),
                    @ApiResponse(responseCode = "404", description = "Parada o monopatín no encontrados")
            }
    )
    @PutMapping("/{idParada}/scooters")
    public ResponseEntity<?> addScooterToParada(@PathVariable String idParada, @RequestBody ScooterIDRequest request) {
        String scooterID = request.getScooterID();
        paradaService.addScooterToParada(idParada, scooterID);

        return ResponseEntity.ok().build();
    }


    //Eliminar monopatin de la parada
    @Operation(
            summary = "Eliminar un monopatín de una parada",
            description = "Este endpoint permite eliminar un monopatín de una parada específica utilizando el ID de la parada y el ID del monopatín.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Monopatín eliminado exitosamente de la parada"),
                    @ApiResponse(responseCode = "404", description = "Parada o monopatín no encontrados")
            }
    )
    @DeleteMapping("/{idParada}/scooters")
    public ResponseEntity<?> removeScooterFromParada(@PathVariable String idParada, @RequestBody ScooterIDRequest request) {
        String scooterID = request.getScooterID();

        paradaService.removeScooterFromParada(idParada, scooterID);
        return ResponseEntity.ok().build();
    }
}
