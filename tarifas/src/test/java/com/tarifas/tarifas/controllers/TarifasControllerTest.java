package com.tarifas.tarifas.controllers;

import com.tarifas.tarifas.DTOs.TarifaDTO;
import com.tarifas.tarifas.exceptions.TarifasNotFoundException;
import com.tarifas.tarifas.services.TarifasService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class TarifasControllerTest {
    @Mock
    private TarifasService tarifasService;

    @InjectMocks
    private TarifasController tarifasController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getTarifas_withType_returnsOk() {
        TarifaDTO mockedTarifa = new TarifaDTO(
                22L,
                "Mocked tarifa",
                new BigDecimal("2232.2"),
                "Tarifa mock"
        );

        when(tarifasService.getTarifaPorTipo("Mocked tarifa")).thenReturn(mockedTarifa);

        ResponseEntity<?> response = tarifasController.getTarifas("Mocked tarifa");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(tarifasService, times(1)).getTarifaPorTipo("Mocked tarifa");
    }

    @Test
    void getTarifas_throwsTarifasNotFoundException_returnsNotFound() {
        when(tarifasService.getTarifaPorTipo(anyString())).thenThrow(new TarifasNotFoundException("Not found"));

        ResponseEntity<?> response = tarifasController.getTarifas("Invalid");

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(tarifasService, times(1)).getTarifaPorTipo("Invalid");
    }

    @Test
    void createTarifa_returnsCreated() {
        TarifaDTO tarifaDTO = new TarifaDTO();
        when(tarifasService.upsertTarifa(tarifaDTO)).thenReturn(new TarifaDTO());

        ResponseEntity<?> response = tarifasController.createTarifa(tarifaDTO);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        verify(tarifasService, times(1)).upsertTarifa(tarifaDTO);
    }

    @Test
    void updateTarifas_returnsOk() {
        TarifaDTO tarifaDTO = new TarifaDTO();
        when(tarifasService.upsertTarifa(tarifaDTO)).thenReturn(new TarifaDTO());

        ResponseEntity<?> response = tarifasController.updateTarifas(tarifaDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(tarifasService, times(1)).upsertTarifa(tarifaDTO);
    }
}
