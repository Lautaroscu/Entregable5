package com.reportes.reportes.controllers;

import com.reportes.reportes.DTOs.TarifaDTO;
import com.reportes.reportes.entities.Tarifa;
import com.reportes.reportes.exceptions.TarifasNotFoundException;
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
            @RequestParam() LocalDateTime startDate,
            @RequestParam() LocalDateTime endDate
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
    public ResponseEntity<?> getTarifas(@RequestParam(value = "tipo", required = false) String tipo) {
        try {
            if (tipo != null) {
                return ResponseEntity.status(HttpStatus.OK).body(reporteService.getTarifaPorTipo(tipo));
            } else {
                return ResponseEntity.status(HttpStatus.OK).body(reporteService.getTarifas());
            }
        } catch (TarifasNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<?> createTarifa(@RequestBody TarifaDTO tarifa) {
        return ResponseEntity.status(HttpStatus.CREATED).body(reporteService.upsertTarifa(tarifa));
    }

    @PutMapping
    public ResponseEntity<?> updateTarifas(@RequestBody TarifaDTO tarifa) {
        return ResponseEntity.status(HttpStatus.OK).body(reporteService.upsertTarifa(tarifa));
    }
}
