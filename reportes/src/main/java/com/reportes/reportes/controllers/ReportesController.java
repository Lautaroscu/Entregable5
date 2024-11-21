package com.reportes.reportes.controllers;

import com.reportes.reportes.services.ReporteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@Tag(name = "Controlador de reportes", description = "Controlador que recibe consultas para reportes")
@RestController
@RequestMapping("/api/reportes")
public class ReportesController {

    private final ReporteService reporteService;

    @Autowired
    public ReportesController(ReporteService reporteService) {
        this.reporteService = reporteService;
    }

    @Operation(
            summary = "Reporte tiempo de uso monopatines",
            description = "Este endpoint devuelve un reporte con el tiempo de uso de todos los monopatines, con pausa o sin pausas.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Respuesta OK"),
                    @ApiResponse(responseCode = "500", description = "Error")
            }
    )
    @GetMapping("/tiempo-de-uso-monopatin")
    public ResponseEntity<?> getReporteUsoMonopatinesPorTiempo(
            @Parameter(
                    description = "Indica si el reporte debe incluir tiempos de uso con pausas (true) o sin pausas (false). " +
                            "Valor por defecto: false."
            )
            @RequestParam(required = false) boolean conPausas
    ) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(reporteService.getReporteUsoMonopatinesPorTiempo(conPausas));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @Operation(
            summary = "Reporte kilometros por monopatin",
            description = "Este endpoint devuelve un reporte con la cantidad de kilometros recorridos por cada monopatin",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Respuesta OK"),
                    @ApiResponse(responseCode = "500", description = "Error")
            }
    )
    @GetMapping("/kilometros-por-monopatin")
    public ResponseEntity<?> getReporteKilometrosPorMonopatin() {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(reporteService.getReporteKilometrosPorMonopatin());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @Operation(
            summary = "Reporte cantidad viajes por monopatin",
            description = "Este endpoint devuelve un reporte con la cantidad de viajes completados realizados por cada monopatin",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Respuesta OK"),
                    @ApiResponse(responseCode = "500", description = "Error")
            }
    )
    @GetMapping("/viajes-por-monopatin")
    public ResponseEntity<?> getReporteScootersPorCantViajes(
            @Parameter(description = "Cantidad mínima de viajes realizados por el monopatín.", required = true, example = "5")
            @RequestParam int cantViajesMinimos,

            @Parameter(description = "Fecha y hora de inicio del reporte.", required = true, example = "2023-01-01T00:00:00")
            @RequestParam LocalDateTime startDate,

            @Parameter(description = "Fecha y hora de fin del reporte. Si no se proporciona, se utiliza la fecha y hora actual.", required = false, example = "2023-12-31T23:59:59")
            @RequestParam(required = false) LocalDateTime endDate
    ) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(
                    reporteService.getReporteScootersPorCantViajes(cantViajesMinimos, startDate, endDate)
            );
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @Operation(
            summary = "Reporte total facturado",
            description = "Este endpoint devuelve un reporte con el monto total facturado entre dos fechas dadas",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Respuesta OK"),
                    @ApiResponse(responseCode = "500", description = "Error")
            }
    )
    @GetMapping("/total-facturado")
    public ResponseEntity<?> getReporteTotalFacturado(
            @Parameter(description = "Fecha y hora de inicio del reporte.", required = true, example = "2023-01-01T00:00:00")
            @RequestParam() LocalDateTime startDate,
            @Parameter(description = "Fecha y hora de fin del reporte.", required = true, example = "2023-03-01T00:00:00")
            @RequestParam() LocalDateTime endDate
    ) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(reporteService.getReporteTotalFacturado(startDate, endDate));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @Operation(
            summary = "Reporte cantidad de monopatines",
            description = "Este endpoint devuelve un reporte con la cantidad de monopatines activos y en mantenimiento",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Respuesta OK"),
                    @ApiResponse(responseCode = "500", description = "Error")
            }
    )
    @GetMapping("/cantidad-monopatines")
    public ResponseEntity<?> getReporteCantidadMonopatinesActivosYEnMantenimiento() {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(
                    reporteService.getReporteCantMonopatinesActivosYEnMantenimiento()
            );
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}
