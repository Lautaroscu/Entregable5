package com.reportes.reportes.controllers;

import com.reportes.reportes.DTOs.*;
import com.reportes.reportes.clients.models.CuentaDTO;
import com.reportes.reportes.services.ReporteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpServerErrorException;

import java.time.LocalDate;
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
    public List<ReporteTiempoUsoMonopatinDTO> getReporteUsoMonopatinesPorTiempo(
            @RequestParam(required = false) boolean conPausas) {
        return reporteService.getReporteUsoMonopatinesPorTiempo(conPausas);
    }

    @GetMapping("/kilometros-por-monopatin")
    public List<ReporteKilometrosMonopatinDTO> getReporteKilometrosPorMonopatin() {
        return reporteService.getReporteKilometrosPorMonopatin();
    }

    @PatchMapping("/anular-cuenta/{id}")
    public CuentaDTO anularCuenta(
            @RequestParam(required = true) long idCuenta
    ) {
        return reporteService.anularCuenta(idCuenta);
    }

    @GetMapping("/viajes-por-monopatin")
    public List<ReporteScootersCantViajesDTO> getReporteScootersPorCantViajes(
            @RequestParam() int cantViajesMinimos,
            @RequestParam() LocalDateTime startDate,
            @RequestParam(required = false) LocalDateTime endDate
    ) {
        return reporteService.getReporteScootersPorCantViajes(cantViajesMinimos, startDate, endDate);
    }

    @GetMapping("/total-facturado")
    public ReporteFacturacion getReporteTotalFacturado(
            @RequestParam(required = true) LocalDateTime startDate,
            @RequestParam(required = true) LocalDateTime endDate
    ) {
        return reporteService.getReporteTotalFacturado(startDate, endDate);
    }

    @GetMapping("/cantidad-monopatines")
    public ReporteCantidadMonopatines getReporteCantidadMonopatinesActivosYEnMantenimiento() {
       return reporteService.getReporteCantMonopatinesActivosYEnMantenimiento();
    }

    @PostMapping
    public List ajusteDePrecios() {
        throw new UnsupportedOperationException("Not supported yet.");
    }


}
