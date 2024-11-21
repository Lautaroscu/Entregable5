package com.viajes.viajes.controllers;

import com.viajes.viajes.DTO.TripInputDTO;
import com.viajes.viajes.DTO.TripOutputDTO;
import com.viajes.viajes.exceptions.ScooterNotFoundException;
import com.viajes.viajes.exceptions.TripNotFoundException;
import com.viajes.viajes.services.TripService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/trips")
public class TripController {
    private final TripService tripService;

    @Autowired
    public TripController(TripService tripService) {
        this.tripService = tripService;
    }

    @Operation(
            summary = "Crear un nuevo viaje",
            description = "Este endpoint permite crear un nuevo viaje. Se debe proporcionar toda la información requerida.",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Viaje creado exitosamente"),
                    @ApiResponse(responseCode = "400", description = "Solicitud incorrecta")
            }
    )
    @PostMapping
    public ResponseEntity<TripOutputDTO> createTrip(@RequestBody TripInputDTO tripInputDTO) {
        tripService.createTrip(tripInputDTO);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Operation(
            summary = "Obtener todos los viajes",
            description = "Devuelve una lista de todos los viajes registrados.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Lista obtenida exitosamente")
            }
    )
    @GetMapping
    public ResponseEntity<List<TripOutputDTO>> getAllTrips() {
        return ResponseEntity.status(HttpStatus.OK).body(tripService.getAllTrips());
    }

    @Operation(
            summary = "Obtener viajes por ID del scooter",
            description = "Devuelve una lista de viajes realizados por un scooter específico.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Lista obtenida exitosamente"),
                    @ApiResponse(responseCode = "404", description = "Scooter no encontrado")
            }
    )
    @GetMapping("/scooter/{scooterID}")
    public ResponseEntity<List<TripOutputDTO>> getAllTripsByScooter(@PathVariable String scooterID) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(tripService.getAllByScooterID(scooterID));
        } catch (ScooterNotFoundException exc) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @Operation(
            summary = "Pausar un viaje",
            description = "Permite pausar un viaje en curso.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Viaje pausado exitosamente"),
                    @ApiResponse(responseCode = "404", description = "Viaje no encontrado")
            }
    )
    @PatchMapping("/pause/{tripID}")
    public ResponseEntity<?> PauseTrip(@PathVariable String tripID) {
        try {
            tripService.setPauseTrip(tripID);
            return ResponseEntity.status(HttpStatus.OK).body("Trip paused successfully");
        } catch (TripNotFoundException exc) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exc.getMessage());
        }
    }

    @Operation(
            summary = "Reanudar un viaje",
            description = "Permite reanudar un viaje que estaba pausado.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Viaje reanudado exitosamente"),
                    @ApiResponse(responseCode = "404", description = "Viaje no encontrado")
            }
    )
    @PatchMapping("/unpause/{tripID}")
    public ResponseEntity<?> UnPauseTrip(@PathVariable String tripID) {
        try {
            tripService.setUnPauseTrip(tripID);
            return ResponseEntity.status(HttpStatus.OK).body("Trip unpaused successfully");
        } catch (TripNotFoundException exc) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exc.getMessage());
        }
    }

    @Operation(
            summary = "Actualizar precio de un viaje",
            description = "Permite actualizar el precio de un viaje según el tipo de tarifa especificado.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Precio actualizado exitosamente"),
                    @ApiResponse(responseCode = "404", description = "Viaje no encontrado")
            }
    )
    @PatchMapping("/update-price/{tripID}/tipo-tarifa/{tipoTarifa}")
    public ResponseEntity<TripOutputDTO> updatePrice(@PathVariable String tripID, @PathVariable String tipoTarifa) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(tripService.updatePrice(tripID, tipoTarifa));
        } catch (TripNotFoundException exc) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @Operation(
            summary = "Finalizar un viaje",
            description = "Permite finalizar un viaje en curso.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Viaje finalizado exitosamente"),
                    @ApiResponse(responseCode = "404", description = "Viaje no encontrado")
            }
    )
    @PostMapping("/endTrip/{tripID}")
    public ResponseEntity<TripOutputDTO> EndTrip(@PathVariable String tripID) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(tripService.EndTrip(tripID));
        } catch (TripNotFoundException exc) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
}
