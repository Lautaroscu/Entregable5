package com.monopatines.monopatines.service;

import com.monopatines.monopatines.DTO.Parada.ParadaInputDTO;
import com.monopatines.monopatines.DTO.Parada.ParadaOutputDTO;
import com.monopatines.monopatines.entities.Parada;
import com.monopatines.monopatines.exceptions.ParadaNotFound;
import com.monopatines.monopatines.repositories.ParadaRepository;
import com.monopatines.monopatines.services.ParadaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ParadaServiceTest {

    @Mock
    private ParadaRepository paradaRepository;

    @InjectMocks
    private ParadaService paradaService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createParada_success() {
        ParadaInputDTO inputDTO = new ParadaInputDTO("Parada Central", 40.5, -3.7);
        Parada parada = new Parada(inputDTO.getNombreParada(), inputDTO.getLatitudParada(), inputDTO.getLongitudParada());

        when(paradaRepository.save(any(Parada.class))).thenReturn(parada);

        ParadaOutputDTO result = paradaService.createParada(inputDTO);

        assertNotNull(result);
        assertEquals("Parada Central", result.getNombreParada());
        verify(paradaRepository, times(1)).save(any(Parada.class));
    }

    @Test
    void getAllParadas_success() {
        Parada parada1 = new Parada("Parada 1", 40.5, -3.7);
        Parada parada2 = new Parada("Parada 2", 41.0, -3.9);

        when(paradaRepository.findAll()).thenReturn(List.of(parada1, parada2));

        List<ParadaOutputDTO> result = paradaService.getAllParadas();

        assertEquals(2, result.size());
        verify(paradaRepository, times(1)).findAll();
    }

    @Test
    void getParadaById_paradaExists_returnsParada() {
        Parada parada = new Parada("Parada Central", 40.5, -3.7);
        parada.setIdParada("123");

        when(paradaRepository.findById("123")).thenReturn(Optional.of(parada));

        ParadaOutputDTO result = paradaService.getParadaById("123");

        assertNotNull(result);
        assertEquals("123", result.getIdParada());
        verify(paradaRepository, times(1)).findById("123");
    }

    @Test
    void getParadaById_paradaNotFound_throwsException() {
        when(paradaRepository.findById("123")).thenReturn(Optional.empty());

        assertThrows(ParadaNotFound.class, () -> paradaService.getParadaById("123"));
        verify(paradaRepository, times(1)).findById("123");
    }

    @Test
    void deleteParada_success() {
        when(paradaRepository.existsById("123")).thenReturn(true);

        paradaService.deleteParada("123");

        verify(paradaRepository, times(1)).deleteById("123");
    }

    @Test
    void deleteParada_paradaNotFound_throwsException() {
        when(paradaRepository.existsById("123")).thenReturn(false);

        assertThrows(ParadaNotFound.class, () -> paradaService.deleteParada("123"));
        verify(paradaRepository, never()).deleteById(anyString());
    }
}
