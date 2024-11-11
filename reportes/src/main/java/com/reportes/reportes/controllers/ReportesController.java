package com.reportes.reportes.controllers;

import com.reportes.reportes.DTOs.TarifaDTO;
import com.reportes.reportes.services.ReporteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/reportes")
public class ReportesController {

    private final ReporteService reporteService;

    @Autowired
    public ReportesController(ReporteService reporteService) {
        this.reporteService = reporteService;
    }

    @GetMapping("/tiempo-de-uso-monopatin")
    public ResponseEntity<?> getReporteUsoMonopatinesPorTiempo(
            @RequestParam(required = false) boolean conPausas
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(reporteService.getReporteUsoMonopatinesPorTiempo(conPausas));
    }

    @GetMapping("/kilometros-por-monopatin")
    public ResponseEntity<?> getReporteKilometrosPorMonopatin() {
        return ResponseEntity.status(HttpStatus.OK).body(reporteService.getReporteKilometrosPorMonopatin());
    }

    @GetMapping("/viajes-por-monopatin")
    public ResponseEntity<?> getReporteScootersPorCantViajes(
            @RequestParam() int cantViajesMinimos,
            @RequestParam() LocalDateTime startDate,
            @RequestParam(required = false) LocalDateTime endDate
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(
                reporteService.getReporteScootersPorCantViajes(cantViajesMinimos, startDate, endDate)
        );
    }

    @GetMapping("/total-facturado")
    public ResponseEntity<?> getReporteTotalFacturado(
            @RequestParam(required = true) LocalDateTime startDate,
            @RequestParam(required = true) LocalDateTime endDate
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(reporteService.getReporteTotalFacturado(startDate, endDate));
    }

    @GetMapping("/cantidad-monopatines")
    public ResponseEntity<?> getReporteCantidadMonopatinesActivosYEnMantenimiento() {
        return ResponseEntity.status(HttpStatus.OK).body(
                reporteService.getReporteCantMonopatinesActivosYEnMantenimiento()
        );
    }

    @GetMapping("/tarifas")
    public ResponseEntity<?> getTarifas(@RequestBody List<TarifaDTO> tarifas) {
        return ResponseEntity.status(HttpStatus.OK).body(reporteService.upsertTarifa(tarifas));
    }

    @PostMapping
    public ResponseEntity<?> createTarifa(@RequestBody List<TarifaDTO> tarifas) {
        return ResponseEntity.status(HttpStatus.CREATED).body(reporteService.upsertTarifa(tarifas));
    }

    @PutMapping
    public ResponseEntity<?> updateTarifas(@RequestBody List<TarifaDTO> tarifas) {
        return ResponseEntity.status(HttpStatus.OK).body(reporteService.upsertTarifa(tarifas));
    }
}
