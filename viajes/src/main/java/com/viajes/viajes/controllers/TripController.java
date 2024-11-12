package com.viajes.viajes.controllers;

import com.viajes.viajes.DTO.TripInputDTO;
import com.viajes.viajes.exceptions.ScooterNotFoundException;
import com.viajes.viajes.exceptions.TripNotFoundException;
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

    @GetMapping("/scooter/{scooterID}")
     public ResponseEntity<?>getAllTripsByScooter(@PathVariable String scooterID){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(tripService.getAllByScooterID(scooterID));
        }
        catch (ScooterNotFoundException exc){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exc.getMessage());
        }
    }


    @PatchMapping("/pause/{tripID}")
    public ResponseEntity<?> PauseTrip(@PathVariable String tripID){
        try{
            tripService.setPauseTrip(tripID);
            return ResponseEntity.status(HttpStatus.OK).body("Trip paused succesfully");
        }
        catch(TripNotFoundException exc){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exc.getMessage());
        }
    }
    @PatchMapping("/unpause/{tripID}")
    public ResponseEntity<?> UnPauseTrip(@PathVariable String tripID){
        try{
            tripService.setUnPauseTrip(tripID);
            return ResponseEntity.status(HttpStatus.OK).body("Trip unpaused succesfully");
        }
        catch(TripNotFoundException exc){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exc.getMessage());
        }
    }

    @PatchMapping("/update-price/{tripID}/tipo-tarifa/{tipoTarifa}")
    public ResponseEntity<?> updatePrice(@PathVariable String tripID , @PathVariable String tipoTarifa){
        try{

            return ResponseEntity.status(HttpStatus.OK).body( tripService.updatePrice(tripID , tipoTarifa));
        }
        catch(TripNotFoundException exc){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exc.getMessage());
        }
    }

    @PostMapping("/endTrip/{tripID}")
    public ResponseEntity<?> EndTrip(@PathVariable String tripID ){
        try{
            return ResponseEntity.status(HttpStatus.OK).body( tripService.EndTrip(tripID));
        }
        catch(TripNotFoundException exc){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exc.getMessage());
        }
    }



}
