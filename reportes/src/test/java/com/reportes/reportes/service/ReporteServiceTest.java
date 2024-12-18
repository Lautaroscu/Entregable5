package com.reportes.reportes.service;

import com.reportes.reportes.DTOs.*;
import com.reportes.reportes.clients.ScooterClient;
import com.reportes.reportes.clients.ViajesClient;
import com.reportes.reportes.clients.models.*;
import com.reportes.reportes.services.ReporteService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

class ReporteServiceTest {

    @Mock
    private ViajesClient viajesClient;

    @Mock
    private ScooterClient scooterClient;

    @InjectMocks
    private ReporteService reporteService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getReporteUsoMonopatinesPorTiempo_withConPausas_returnsExpectedResult() {
        ScooterDTO mockedScooterDTO = getMockedScooterDTO();
        List<ScooterDTO> mockScooters = List.of(mockedScooterDTO);


        when(scooterClient.getScooters()).thenReturn(mockScooters);
        when(viajesClient.getViajesByScooterId(anyString())).thenReturn(getMockedListViajeDTO(1));

        List<ReporteTiempoUsoMonopatinDTO> result = reporteService.getReporteUsoMonopatinesPorTiempo(true);

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(scooterClient, times(1)).getScooters();
        verify(viajesClient, times(1)).getViajesByScooterId(anyString());
    }

    @Test
    void getReporteKilometrosPorMonopatin_returnsExpectedResult() {
        List<ScooterDTO> mockScooters = List.of(getMockedScooterDTO());

        when(scooterClient.getScooters()).thenReturn(mockScooters);
        when(viajesClient.getViajesByScooterId(anyString())).thenReturn(getMockedListViajeDTO(1));

        List<ReporteKilometrosMonopatinDTO> result = reporteService.getReporteKilometrosPorMonopatin();

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(scooterClient, times(1)).getScooters();
        verify(viajesClient, times(1)).getViajesByScooterId(anyString());
    }

    @Test
    void testGetReporteScootersPorCantViajes() {
        ScooterDTO mockedScooterDTO = getMockedScooterDTO();
        List<ScooterDTO> scooters = List.of(mockedScooterDTO);
        when(scooterClient.getScooters()).thenReturn(scooters);

        when(viajesClient.getViajesByScooterId(anyString())).thenReturn(getMockedListViajeDTO(2));

        List<ReporteScootersCantViajesDTO> result = reporteService.getReporteScootersPorCantViajes(1, LocalDateTime.now().minusDays(2), LocalDateTime.now().plusDays(1));

        assertEquals(1, result.size());
        assertEquals(2, result.getFirst().getCantViajes());
        assertEquals(mockedScooterDTO, result.getFirst().getScooterDTO());
    }

    @Test
    void testGetReporteTotalFacturado() {
        LocalDateTime startDate = LocalDateTime.now().minusDays(2);
        LocalDateTime endDate = LocalDateTime.now().plusDays(2);
        double finalPriceTrip1 = 10.0;
        double finalPriceTrip2 = 20.5;

        List<ViajeDTO> mockedListViajeDTO = getMockedListViajeDTO(2);

        mockedListViajeDTO.get(0).setFinalPrice(finalPriceTrip1);

        mockedListViajeDTO.get(1).setTripID("mockedTrip2");
        mockedListViajeDTO.get(1).setFinalPrice(finalPriceTrip2);

        when(viajesClient.getViajes()).thenReturn(mockedListViajeDTO);

        ReporteFacturacion result = reporteService.getReporteTotalFacturado(startDate, endDate);

        assertEquals(finalPriceTrip1 + finalPriceTrip2, result.getCantFacturado());
    }

    @Test
    void testGetReporteCantMonopatinesActivosYEnMantenimiento() {
        ScooterDTO scooterAvailable = new ScooterDTO();
        ScooterDTO scooterMaintenance = new ScooterDTO();

        scooterAvailable.setId("scooter1");
        scooterMaintenance.setId("scooter2");

        scooterAvailable.setStatus(ScooterStatus.AVAILABLE);
        scooterMaintenance.setStatus(ScooterStatus.UNDER_MAINTENANCE);

        when(scooterClient.getScooters(ScooterStatus.AVAILABLE)).thenReturn(List.of(scooterAvailable));
        when(scooterClient.getScooters(ScooterStatus.UNDER_MAINTENANCE)).thenReturn(List.of(scooterMaintenance));

        ReporteCantidadMonopatines result = reporteService.getReporteCantMonopatinesActivosYEnMantenimiento();

        assertEquals(1, result.getCantidadMonopatinesActivos());
        assertEquals(1, result.getCantidadMonopatinesEnMantenimiento());
    }

    private ScooterDTO getMockedScooterDTO() {
        return new ScooterDTO(
                "1", ScooterStatus.AVAILABLE, "Tandil", 40.8, 20
        );
    }

    private List<ViajeDTO> getMockedListViajeDTO(int size) {
        ScooterDTO mockedScooterDTO = getMockedScooterDTO();
        Account mockedAccount = new Account(23L, LocalDate.now());
        List<ViajeDTO> mockedListViajes = new ArrayList<>(size);

        for (int i = 1; i <= size; i++) {
            mockedListViajes.add(
                    new ViajeDTO(
                            "mockedId",
                            mockedScooterDTO,
                            mockedAccount,
                            LocalDateTime.now(),
                            LocalDateTime.now().plusHours(2),
                            23.2,
                            TripStatus.COMPLETED,
                            10,
                            LocalDateTime.now().plusMinutes(10),
                            LocalDateTime.now().plusMinutes(20)
                    )
            );
        }

        return mockedListViajes;
    }
}
