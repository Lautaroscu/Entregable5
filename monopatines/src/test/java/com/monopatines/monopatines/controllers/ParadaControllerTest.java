package com.monopatines.monopatines.controllers;

import com.monopatines.monopatines.DTO.Parada.ParadaInputDTO;
import com.monopatines.monopatines.DTO.Parada.ParadaOutputDTO;
import com.monopatines.monopatines.DTO.Parada.ScooterIDRequest;
import com.monopatines.monopatines.entities.Parada;
import com.monopatines.monopatines.exceptions.ParadaNotFound;
import com.monopatines.monopatines.services.ParadaService;
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

class ParadaControllerTest {

    @Mock
    private ParadaService paradaService;

    @InjectMocks
    private ParadaController paradaController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createParada_returnsCreated() {
        ParadaInputDTO inputDTO = new ParadaInputDTO("Parada A",0.0,0.0);

        // Mock de Parada
        Parada parada = new Parada();
        parada.setIdParada("1");
        parada.setNombreParada("Test Parada");
        parada.setLatitudParada(0.0);
        parada.setLongitudParada(0.0);

        ParadaOutputDTO outputDTO = new ParadaOutputDTO(parada);

        when(paradaService.createParada(inputDTO)).thenReturn(outputDTO);

        ResponseEntity<ParadaOutputDTO> response = paradaController.createParada(inputDTO);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(outputDTO, response.getBody());
        verify(paradaService, times(1)).createParada(inputDTO);
    }

    @Test
    void getAllParadas_returnsOk() {
        Parada parada = new Parada();
        parada.setIdParada("1");
        parada.setNombreParada("Parada A");
        parada.setLatitudParada(0.0);
        parada.setLongitudParada(0.0);
        parada.setScooters(Collections.emptyList());

        List<ParadaOutputDTO> mockedList = Collections.singletonList(new ParadaOutputDTO(parada));

        when(paradaService.getAllParadas()).thenReturn(mockedList);

        ResponseEntity<List<ParadaOutputDTO>> response = paradaController.getAllParadas();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockedList, response.getBody());
        verify(paradaService, times(1)).getAllParadas();
    }

    @Test
    void getParadaById_returnsOk() {
        String id = "1";

        Parada parada = new Parada();
        parada.setIdParada(id);
        parada.setNombreParada("Parada A");
        parada.setLatitudParada(0.0);
        parada.setLongitudParada(0.0);

        ParadaOutputDTO outputDTO = new ParadaOutputDTO(parada);

        when(paradaService.getParadaById(id)).thenReturn(outputDTO);

        ResponseEntity<?> response = paradaController.getParadaById(id);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(outputDTO, response.getBody());
        verify(paradaService, times(1)).getParadaById(id);
    }

    @Test
    void getParadaById_throwsParadaNotFound_returnsNotFound() {
        String id = "1";

        when(paradaService.getParadaById(id)).thenThrow(new ParadaNotFound("Parada not found"));

        ResponseEntity<?> response = paradaController.getParadaById(id);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Parada not found", response.getBody());
        verify(paradaService, times(1)).getParadaById(id);
    }

    @Test
    void addScooterToParada_returnsOk() {
        String idParada = "1";
        ScooterIDRequest request = new ScooterIDRequest("123");

        ResponseEntity<?> response = paradaController.addScooterToParada(idParada, request);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(paradaService, times(1)).addScooterToParada(idParada, request.getScooterID());
    }

    @Test
    void removeScooterFromParada_returnsOk() {
        String idParada = "1";
        ScooterIDRequest request = new ScooterIDRequest("123");

        ResponseEntity<?> response = paradaController.removeScooterFromParada(idParada, request);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(paradaService, times(1)).removeScooterFromParada(idParada, request.getScooterID());
    }
}
