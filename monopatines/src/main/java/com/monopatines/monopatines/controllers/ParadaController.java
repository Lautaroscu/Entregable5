package com.monopatines.monopatines.controllers;

import com.monopatines.monopatines.DTO.Parada.ParadaInputDTO;
import com.monopatines.monopatines.DTO.Parada.ParadaOutputDTO;
import com.monopatines.monopatines.DTO.Parada.ScooterIDRequest;
import com.monopatines.monopatines.exceptions.ParadaNotFound;
import com.monopatines.monopatines.services.ParadaService;
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
    @PostMapping
    public ResponseEntity<ParadaOutputDTO> createParada(@RequestBody ParadaInputDTO paradaInputDTO) {
        ParadaOutputDTO paradaOutputDTO = paradaService.createParada(paradaInputDTO);
        return new ResponseEntity<>(paradaOutputDTO, HttpStatus.CREATED);
    }

    // Obtener todas las paradas
    @GetMapping
    public ResponseEntity<List<ParadaOutputDTO>> getAllParadas() {
        List<ParadaOutputDTO> paradas = paradaService.getAllParadas();
        return new ResponseEntity<>(paradas, HttpStatus.OK);
    }

    // Obtener una parada por ID
    @GetMapping("/{id}")
    public ResponseEntity<ParadaOutputDTO> getParadaById(@PathVariable String id) {
        ParadaOutputDTO paradaOutputDTO = paradaService.getParadaById(id);
        return new ResponseEntity<>(paradaOutputDTO, HttpStatus.OK);
    }

    // Actualizar el nombre de una parada
    @PutMapping("/{id}")
    public ResponseEntity<ParadaOutputDTO> updateParada(@PathVariable String id, @RequestBody ParadaInputDTO paradaInputDTO) {
        ParadaOutputDTO paradaOutputDTO = paradaService.updateParada(paradaInputDTO, id);
        return new ResponseEntity<>(paradaOutputDTO, HttpStatus.OK);
    }

    // Eliminar una parada
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteParada(@PathVariable String id) {
        paradaService.deleteParada(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{idParada}/scooters")
    public ResponseEntity<?> addScooterToParada(@PathVariable String idParada, @RequestBody ScooterIDRequest request) {
        String scooterID = request.getScooterID();
        paradaService.addScooterToParada(idParada, scooterID);

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{idParada}/scooters")
    public ResponseEntity<?> removeScooterFromParada(@PathVariable String idParada, @RequestBody ScooterIDRequest request) {
        String scooterID = request.getScooterID();

        paradaService.removeScooterFromParada(idParada, scooterID);
        return ResponseEntity.ok().build();
    }
}
