package com.viajes.viajes.services;

import com.viajes.viajes.DTO.TripInputDTO;
import com.viajes.viajes.DTO.TripOutputDTO;
import com.viajes.viajes.clients.AccountClient;
import com.viajes.viajes.clients.ScooterClient;
import com.viajes.viajes.clients.TarifasClient;
import com.viajes.viajes.clients.models.Account;
import com.viajes.viajes.clients.models.ScooterDTO;
import com.viajes.viajes.entities.Trip;
import com.viajes.viajes.repositories.TripRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@SpringBootTest
public class ViajesServicesTest {

    @Mock
    private TripRepository tripRepository;

    @Mock
    private AccountClient accountClient;

    @Mock
    private ScooterClient scooterClient;

    @Mock
    private TarifasClient tarifasClient;

    @InjectMocks
    private TripService tripService;

    private TripInputDTO tripInputDTO;
    private Trip trip;
    private Account account;
    private ScooterDTO scooterDTO;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        tripInputDTO = new TripInputDTO("scooterId", 22L);
        account = new Account(22L, LocalDate.now(), 200.0, "CuentaMp", false);
        ScooterDTO scooter = new ScooterDTO(
                "scooterId",
                "Available",
                200.0,
                200.0,
                40.7128,
                -74.0060
        );

        trip = new Trip(scooterDTO, account);
        trip.setTripID("tripId");
    }

    @Test
    public void testCreateTrip() {
        when(accountClient.getAccountById(anyLong())).thenReturn(account);
        when(scooterClient.getScooterById(anyString())).thenReturn(scooterDTO);
        when(tripRepository.save(any(Trip.class))).thenReturn(trip);

        TripOutputDTO result = tripService.createTrip(tripInputDTO);

        assertNotNull(result);
    }
}

