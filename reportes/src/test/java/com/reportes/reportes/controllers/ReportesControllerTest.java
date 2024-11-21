package com.reportes.reportes.controllers;

import com.reportes.reportes.DTOs.ReporteCantidadMonopatines;
import com.reportes.reportes.DTOs.ReporteFacturacion;
import com.reportes.reportes.DTOs.ReporteTiempoUsoMonopatinDTO;
import com.reportes.reportes.clients.models.ScooterDTO;
import com.reportes.reportes.clients.models.ScooterStatus;
import com.reportes.reportes.services.ReporteService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class ReportesControllerTest {

    @Mock
    private ReporteService reporteService;

    @InjectMocks
    private ReportesController reportesController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getReporteUsoMonopatinesPorTiempo_returnsOk() {
        ReporteTiempoUsoMonopatinDTO mockedReporte = new ReporteTiempoUsoMonopatinDTO(
                new ScooterDTO(
                        "1", ScooterStatus.AVAILABLE, "Tandil", 40.8, 20
                ),
                Duration.of(20, ChronoUnit.MINUTES)
        );
        ArrayList<ReporteTiempoUsoMonopatinDTO> mockedList = new ArrayList<>();
        mockedList.add(mockedReporte);

        when(reporteService.getReporteUsoMonopatinesPorTiempo(true)).thenReturn(mockedList);

        ResponseEntity<?> response = reportesController.getReporteUsoMonopatinesPorTiempo(true);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(reporteService, times(1)).getReporteUsoMonopatinesPorTiempo(true);
    }

    @Test
    void getReporteKilometrosPorMonopatin_returnsOk() {
        when(reporteService.getReporteKilometrosPorMonopatin()).thenReturn(Collections.emptyList());

        ResponseEntity<?> response = reportesController.getReporteKilometrosPorMonopatin();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(reporteService, times(1)).getReporteKilometrosPorMonopatin();
    }

    @Test
    void getReporteScootersPorCantViajes_returnsOk() {
        LocalDateTime startDate = LocalDateTime.now().minusDays(1);
        LocalDateTime endDate = LocalDateTime.now();

        when(reporteService.getReporteScootersPorCantViajes(5, startDate, endDate))
                .thenReturn(Collections.emptyList());

        ResponseEntity<?> response = reportesController.getReporteScootersPorCantViajes(5, startDate, endDate);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(reporteService, times(1))
                .getReporteScootersPorCantViajes(5, startDate, endDate);
    }

    @Test
    void getReporteTotalFacturado_returnsOk() {
        LocalDateTime startDate = LocalDateTime.now().minusDays(1);
        LocalDateTime endDate = LocalDateTime.now();
        ReporteFacturacion mockedReporte = new ReporteFacturacion(startDate, endDate, 10000);

        when(reporteService.getReporteTotalFacturado(startDate, endDate)).thenReturn(mockedReporte);

        ResponseEntity<?> response = reportesController.getReporteTotalFacturado(startDate, endDate);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(reporteService, times(1)).getReporteTotalFacturado(startDate, endDate);
        assertEquals(mockedReporte, response.getBody());
    }

    @Test
    void getReporteCantidadMonopatinesActivosYEnMantenimiento_returnsOk() {
        ReporteCantidadMonopatines mockedReporte = new ReporteCantidadMonopatines(
                20,
                40
        );
        when(reporteService.getReporteCantMonopatinesActivosYEnMantenimiento()).thenReturn(mockedReporte);

        ResponseEntity<?> response = reportesController.getReporteCantidadMonopatinesActivosYEnMantenimiento();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(reporteService, times(1)).getReporteCantMonopatinesActivosYEnMantenimiento();
    }
}