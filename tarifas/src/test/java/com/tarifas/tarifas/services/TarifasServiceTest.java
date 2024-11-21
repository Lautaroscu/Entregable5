package com.tarifas.tarifas.services;

import com.tarifas.tarifas.DTOs.TarifaDTO;
import com.tarifas.tarifas.entities.Tarifa;
import com.tarifas.tarifas.repositories.TarifasRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class TarifasServiceTest {

    @Mock
    TarifasRepository tarifasRepository;

    @InjectMocks
    TarifasService tarifasService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetTarifas() {
        Tarifa tarifa1 = new Tarifa(1L, "Tipo1", new BigDecimal("100.0"), "Descripcion1");
        Tarifa tarifa2 = new Tarifa(2L, "Tipo2", new BigDecimal("110.0"), "Descripcion2");
        when(tarifasRepository.findAll()).thenReturn(List.of(tarifa1, tarifa2));

        List<TarifaDTO> result = tarifasService.getTarifas();

        assertEquals(2, result.size());
        assertEquals("Tipo1", result.get(0).getTipoTarifa());
        assertEquals("Tipo2", result.get(1).getTipoTarifa());
    }

    @Test
    void testUpsertTarifa() {
        Tarifa tarifa = new Tarifa(1L, "Tipo1", new BigDecimal("100.0"), "Descripcion1");
        TarifaDTO tarifaDTO = new TarifaDTO(tarifa);
        when(tarifasRepository.save(any(Tarifa.class))).thenReturn(tarifa);

        TarifaDTO result = tarifasService.upsertTarifa(tarifaDTO);

        assertEquals(tarifaDTO.getTipoTarifa(), result.getTipoTarifa());
        assertEquals(tarifaDTO.getMonto(), result.getMonto());
    }

    @Test
    void testGetTarifaPorTipo() {
        BigDecimal valorTarifa = new BigDecimal("100.0");
        Tarifa tarifa = new Tarifa(1L, "Tipo1", valorTarifa, "Descripcion1");
        when(tarifasRepository.findByTipoTarifa("Tipo1")).thenReturn(tarifa);

        TarifaDTO result = tarifasService.getTarifaPorTipo("Tipo1");

        assertEquals("Tipo1", result.getTipoTarifa());
        assertEquals(valorTarifa, result.getMonto());
    }
}
