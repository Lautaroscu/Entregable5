package com.viajes.viajes.controllers;

import com.viajes.viajes.DTO.TripInputDTO;
import com.viajes.viajes.services.TripService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/trips")
public class TripController {
    private final TripService tripService;
    @Autowired
    public TripController(TripService tripService) {
        this.tripService = tripService;
    }
    @PostMapping
    public ResponseEntity<?> createTrip(@RequestBody TripInputDTO tripInputDTO) {
        tripService.createTrip(tripInputDTO);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
    @GetMapping
    public ResponseEntity<?> getAllTrips() {
        return ResponseEntity.status(HttpStatus.OK).body(tripService.getAllTrips());
    }
}
