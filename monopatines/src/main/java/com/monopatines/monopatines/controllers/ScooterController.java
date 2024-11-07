package com.monopatines.monopatines.controllers;

import com.monopatines.monopatines.DTO.ScooterInputDTO;
import com.monopatines.monopatines.DTO.ScooterOutputDTO;
import com.monopatines.monopatines.DTO.ScooterStatusDTO;
import com.monopatines.monopatines.exceptions.BadRequestException;
import com.monopatines.monopatines.exceptions.ScooterNotFound;
import com.monopatines.monopatines.services.ScooterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/scooters")
public class ScooterController {
    private final ScooterService scooterService;
    @Autowired
    public ScooterController(ScooterService scooterService){
        this.scooterService = scooterService;
    }
    @PostMapping
    public ResponseEntity<?> createScooter(@RequestBody ScooterInputDTO scooterInputDTO){
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(scooterService.createScooter(scooterInputDTO));
        }catch (BadRequestException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @GetMapping
    public ResponseEntity<List<ScooterOutputDTO>> getAllScooters(){
        return ResponseEntity.status(HttpStatus.OK).body(scooterService.getAllScooters());
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> getScooterById(@PathVariable String id){
        try {
            return ResponseEntity.status(HttpStatus.OK).body(scooterService.getScooterById(id));
        } catch (ScooterNotFound e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
    @PutMapping("/{id}")
    public ResponseEntity<?> updateScooter(@RequestBody ScooterInputDTO scooterInputDTO , @PathVariable String id) {
    try {
        return ResponseEntity.status(HttpStatus.CREATED).body(scooterService.updateScooter(scooterInputDTO,id));

    } catch (ScooterNotFound e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }catch (BadRequestException e){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteScooter(@PathVariable String id){
        try {
            return ResponseEntity.ok().build();
        }catch (ScooterNotFound e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
    @PatchMapping("/{id}")
    public ResponseEntity<?> setStatus(@PathVariable String id, @RequestBody ScooterStatusDTO scooterStatusDTO){
        try {
            scooterService.setStatus(scooterStatusDTO , id);
            return ResponseEntity.ok().build();
        }catch (ScooterNotFound e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

}
