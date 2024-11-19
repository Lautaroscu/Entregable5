package com.monopatines.monopatines.controllers;

import com.monopatines.monopatines.DTO.Scooter.ScooterFiltersDTO;
import com.monopatines.monopatines.DTO.Scooter.ScooterInputDTO;
import com.monopatines.monopatines.DTO.Scooter.ScooterOutputDTO;
import com.monopatines.monopatines.DTO.Scooter.ScooterStatusDTO;
import com.monopatines.monopatines.Enumns.ScooterStatus;
import com.monopatines.monopatines.entities.Scooter;
import com.monopatines.monopatines.exceptions.BadRequestException;
import com.monopatines.monopatines.exceptions.ScooterNotFound;
import com.monopatines.monopatines.services.ScooterService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class ScooterControllerTest {

    @Mock
    private ScooterService scooterService;

    @InjectMocks
    private ScooterController scooterController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createScooter_returnsCreated() {
        ScooterInputDTO inputDTO = new ScooterInputDTO(123, 123, "AVAILABLE");

        Scooter scooter = new Scooter();
        scooter.setId("1");
        scooter.setModel("Model X");
        scooter.setLatitude(0.0);
        scooter.setLongitude(0.0);


        ScooterOutputDTO outputDTO = new ScooterOutputDTO(scooter);

        when(scooterService.createScooter(inputDTO)).thenReturn(outputDTO);

        ResponseEntity<?> response = scooterController.createScooter(inputDTO);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(outputDTO, response.getBody());
        verify(scooterService, times(1)).createScooter(inputDTO);
    }

    @Test
    void getAllScooters_returnsOk() {
        Scooter scooter = new Scooter();
        scooter.setId("1");
        scooter.setModel("Model X");
        scooter.setLatitude(0.0);
        scooter.setLongitude(0.0);

        List<ScooterOutputDTO> mockedList = Collections.singletonList(new ScooterOutputDTO(scooter));

        when(scooterService.getAllScooters(any(ScooterFiltersDTO.class))).thenReturn(mockedList);

        ResponseEntity<List<ScooterOutputDTO>> response = scooterController.getAllScooters(null, null, null);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockedList, response.getBody());
        verify(scooterService, times(1)).getAllScooters(any(ScooterFiltersDTO.class));
    }

    @Test
    void getScooterById_returnsOk() {
        String id = "1";

        Scooter scooter = new Scooter();
        scooter.setId(id);
        scooter.setModel("Model X");
        scooter.setLatitude(0.0);
        scooter.setLongitude(0.0);

        ScooterOutputDTO outputDTO = new ScooterOutputDTO(scooter);

        when(scooterService.getScooterById(id)).thenReturn(outputDTO);

        ResponseEntity<?> response = scooterController.getScooterById(id);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(outputDTO, response.getBody());
        verify(scooterService, times(1)).getScooterById(id);
    }

    @Test
    void getScooterById_throwsScooterNotFound_returnsNotFound() {
        String id = "1";

        when(scooterService.getScooterById(id)).thenThrow(new ScooterNotFound("Not found"));

        ResponseEntity<?> response = scooterController.getScooterById(id);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(scooterService, times(1)).getScooterById(id);
    }
}