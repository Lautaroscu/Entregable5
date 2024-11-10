package com.reportes.reportes.controllers;

import com.reportes.reportes.DTOs.ReporteKilometrosMonopatinDTO;
import com.reportes.reportes.DTOs.ReporteTiempoUsoMonopatinDTO;
import com.reportes.reportes.services.ReporteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
            @RequestParam(required = false) boolean conPausas){
        return reporteService.getReporteUsoMonopatinesPorTiempo(conPausas);
    }

    @GetMapping("/kilometros-por-monopatin")
    public List<ReporteKilometrosMonopatinDTO> getReporteKilometrosPorMonopatin(){
        return reporteService.getReporteKilometrosPorMonopatin();
    }
}
