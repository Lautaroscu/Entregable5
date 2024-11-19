package com.monopatines.monopatines.service;

import com.monopatines.monopatines.DTO.Scooter.ScooterFiltersDTO;
import com.monopatines.monopatines.DTO.Scooter.ScooterInputDTO;
import com.monopatines.monopatines.DTO.Scooter.ScooterOutputDTO;
import com.monopatines.monopatines.DTO.Scooter.ScooterStatusDTO;
import com.monopatines.monopatines.entities.Scooter;
import com.monopatines.monopatines.exceptions.ScooterNotFound;
import com.monopatines.monopatines.repositories.ScooterRepository;
import com.monopatines.monopatines.services.ScooterService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ScooterServiceTest {

    @Mock
    private ScooterRepository scooterRepository;

    @Mock
    private MongoTemplate mongoTemplate;

    @InjectMocks
    private ScooterService scooterService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createScooter_success() {
        ScooterInputDTO inputDTO = new ScooterInputDTO(40.5, -3.7, "Model X");
        Scooter scooter = new Scooter(inputDTO.getLatitude(), inputDTO.getLongitude(), inputDTO.getModel());

        when(scooterRepository.save(any(Scooter.class))).thenReturn(scooter);

        ScooterOutputDTO result = scooterService.createScooter(inputDTO);

        assertNotNull(result);
        assertEquals("Model X", result.getModel());
        verify(scooterRepository, times(1)).save(any(Scooter.class));
    }

    @Test
    void getScooterById_scooterExists_returnsScooter() {
        Scooter scooter = new Scooter(40.5, -3.7, "Model X");
        scooter.setId("123");

        when(scooterRepository.findById("123")).thenReturn(Optional.of(scooter));

        ScooterOutputDTO result = scooterService.getScooterById("123");

        assertNotNull(result);
        assertEquals("123", result.getId());
        verify(scooterRepository, times(1)).findById("123");
    }

    @Test
    void getScooterById_scooterNotFound_throwsException() {
        when(scooterRepository.findById("123")).thenReturn(Optional.empty());

        assertThrows(ScooterNotFound.class, () -> scooterService.getScooterById("123"));
        verify(scooterRepository, times(1)).findById("123");
    }

    @Test
    void deleteScooter_success() {
        when(scooterRepository.existsById("123")).thenReturn(true);

        scooterService.deleteScooter("123");

        verify(scooterRepository, times(1)).deleteById("123");
    }

    @Test
    void deleteScooter_scooterNotFound_throwsException() {
        when(scooterRepository.existsById("123")).thenReturn(false);

        assertThrows(ScooterNotFound.class, () -> scooterService.deleteScooter("123"));
        verify(scooterRepository, never()).deleteById(anyString());
    }
}
