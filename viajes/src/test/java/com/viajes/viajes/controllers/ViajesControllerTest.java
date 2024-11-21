package com.viajes.viajes.controllers;

import com.viajes.viajes.DTO.TripInputDTO;
import com.viajes.viajes.DTO.TripOutputDTO;
import com.viajes.viajes.clients.models.Account;
import com.viajes.viajes.clients.models.ScooterDTO;
import com.viajes.viajes.clients.models.TarifaDTO;
import com.viajes.viajes.entities.Trip;
import com.viajes.viajes.services.TripService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.viajes.viajes.enumns.TripStatus.COMPLETED;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

public class ViajesControllerTest {

    @Mock
    private TripService viajesService;

    @InjectMocks
    private TripController viajesController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void TestGetViajes() {

        TripOutputDTO mockedtrip = new TripOutputDTO();
        Account mockcuenta = new Account();
        ScooterDTO mockscooter = new ScooterDTO();
        mockedtrip.setAccount(mockcuenta);
        mockedtrip.setScooterDTO(mockscooter);

        ArrayList<TripOutputDTO> mockedList = new ArrayList<>();
        mockedList.add(mockedtrip);
        Mockito.when(viajesService.getAllTrips()).thenReturn(mockedList);
        ResponseEntity<List<TripOutputDTO>> response = viajesController.getAllTrips();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(viajesService, times(1)).getAllTrips();
    }

    @Test
    public void testcrearViaje() {

        ScooterDTO mockScooter = new ScooterDTO("id", "AVAILABLE", 12.0, 4.0, 2.0, 3.0);
        Account mockAcount = new Account(123123L, LocalDate.now(), 10.0, "Cuetamp", true);
        Trip mockedtrip = new Trip(mockScooter, mockAcount);
        TripOutputDTO TripOutputDTO = new TripOutputDTO(mockedtrip);
        TripInputDTO tripEntrada = new TripInputDTO(mockScooter.getId(), mockAcount.getId());
        Mockito.when(viajesService.createTrip(tripEntrada)).thenReturn(TripOutputDTO);

        ResponseEntity<TripOutputDTO> response = viajesController.createTrip(tripEntrada);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    public void testGetViajesPorScooterID() {
        TripOutputDTO mockedtrip = new TripOutputDTO();
        ScooterDTO mockscooter = new ScooterDTO();
        mockscooter.setId("idid");
        TripOutputDTO mockedtrip2 = new TripOutputDTO();
        mockedtrip.setScooterDTO(mockscooter);
        mockedtrip2.setScooterDTO(mockscooter);

        ArrayList<TripOutputDTO> mockedList = new ArrayList<>();
        mockedList.add(mockedtrip);
        mockedList.add(mockedtrip2);
        Mockito.when(viajesService.getAllByScooterID(mockscooter.getId())).thenReturn(mockedList);

        ResponseEntity<List<TripOutputDTO>> response = viajesController.getAllTripsByScooter(mockscooter.getId());

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockedList.getFirst().getTripID(), mockedList.getFirst().getTripID());
    }

    @Test
    public void testPausarViaje() {
        viajesController.PauseTrip("tripID");

        verify(viajesService, times(1)).setPauseTrip(anyString());
    }

    @Test
    public void testReanudarViaje() {
        viajesController.UnPauseTrip("tripID");

        verify(viajesService, times(1)).setUnPauseTrip(anyString());
    }

    @Test
    public void testActualizarPrecioViaje() {

        TripOutputDTO mockedtrip = new TripOutputDTO();
        mockedtrip.setTripID("tripID");
        BigDecimal monto = new BigDecimal(500);
        TarifaDTO tarifa = new TarifaDTO(123123l, "Normal", monto, "description");

        Mockito.when(viajesService.updatePrice(mockedtrip.getTripID(), tarifa.getTipoTarifa())).thenReturn(mockedtrip);
        ResponseEntity<TripOutputDTO> response = this.viajesController.updatePrice(mockedtrip.getTripID(), tarifa.getTipoTarifa());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(this.viajesService, times(1)).updatePrice(mockedtrip.getTripID(), tarifa.getTipoTarifa());
    }

    @Test
    public void testFinalizarViaje() {

        TripOutputDTO mockedtrip = new TripOutputDTO();
        mockedtrip.setTripID("tripID");
        mockedtrip.setPrice(new BigDecimal(1500));
        mockedtrip.setTripStatus(COMPLETED);
        mockedtrip.setStartPauseTime(LocalDateTime.now());

        Mockito.when(viajesService.setPauseTrip(mockedtrip.getTripID())).thenReturn(mockedtrip);
        ResponseEntity<TripOutputDTO> response = viajesController.EndTrip(mockedtrip.getTripID());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(viajesService, times(1)).EndTrip(anyString());
    }
}
