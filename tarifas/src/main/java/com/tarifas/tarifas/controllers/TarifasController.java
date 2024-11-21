package com.tarifas.tarifas.controllers;

import com.tarifas.tarifas.DTOs.TarifaDTO;
import com.tarifas.tarifas.exceptions.TarifasNotFoundException;
import com.tarifas.tarifas.services.TarifasService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Controlador de tarifas", description = "Controlador que recibe consultas CRUD para tarifas")
@RestController
@RequestMapping("/api/tarifas")
public class TarifasController {

    private final TarifasService tarifasService;

    @Autowired
    public TarifasController(TarifasService tarifasService) {
        this.tarifasService = tarifasService;
    }

    @Operation(
            summary = "Obtener tarifas",
            description = "Este endpoint devuelve los datos de todas las tarifas. En caso que se requiera puede buscarse una tarifa por tipo",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Respuesta OK"),
                    @ApiResponse(responseCode = "404", description = "No encontrada")
            }
    )
    @GetMapping()
    public ResponseEntity<?> getTarifas(
            @Parameter(description = "Tipo de tarifa", example = "Normal")
            @RequestParam(value = "tipo", required = false) String tipo
    ) {
        try {
            if (tipo != null) {
                return ResponseEntity.status(HttpStatus.OK).body(tarifasService.getTarifaPorTipo(tipo));
            } else {
                return ResponseEntity.status(HttpStatus.OK).body(tarifasService.getTarifas());
            }
        } catch (TarifasNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
    }

    @Operation(
            summary = "Insertar tarifa",
            description = "Este endpoint se utiliza para insertar una tarifa en la base de datos",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Tarifa creada"),
                    @ApiResponse(responseCode = "500", description = "Error")
            }
    )
    @PostMapping
    public ResponseEntity<?> createTarifa(
            @Parameter(
                    description = "Detalles de la tarifa a crear", required = true,
                    content = @Content(schema = @Schema(implementation = TarifaDTO.class))
            )
            @RequestBody TarifaDTO tarifa
    ) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(tarifasService.upsertTarifa(tarifa));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @Operation(
            summary = "Modificar tarifa",
            description = "Este endpoint se utiliza para modificar una tarifa",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Respuesta modificada"),
                    @ApiResponse(responseCode = "500", description = "Error")
            }
    )
    @PutMapping
    public ResponseEntity<?> updateTarifas(
            @Parameter(
                    description = "Detalles de la tarifa a actualizar o crear", required = true,
                    content = @Content(schema = @Schema(implementation = TarifaDTO.class))
            )
            @RequestBody TarifaDTO tarifa
    ) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(tarifasService.upsertTarifa(tarifa));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}
