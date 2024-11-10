package com.reportes.reportes.services;

import com.reportes.reportes.DTOs.*;
import com.reportes.reportes.clients.CuentaClient;
import com.reportes.reportes.clients.ScooterClient;
import com.reportes.reportes.clients.ViajesClient;
import com.reportes.reportes.clients.models.CuentaDTO;
import com.reportes.reportes.clients.models.ScooterDTO;
import com.reportes.reportes.clients.models.ScooterStatus;
import com.reportes.reportes.clients.models.ViajeDTO;
import com.reportes.reportes.repositories.ReporteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReporteService {
    private final ReporteRepository reporteRepository;
    private final ViajesClient viajesClient;
    private final ScooterClient scooterClient;
    private final CuentaClient cuentaClient;

    @Autowired
    public ReporteService(
            ReporteService reporteService,
            ViajesClient viajesClient,
            ScooterClient scooterClient,
            CuentaClient cuentaClient) {
        this.reporteRepository = reporteService.reporteRepository;
        this.viajesClient = viajesClient;
        this.scooterClient = scooterClient;
        this.cuentaClient = cuentaClient;
    }

    public List<ReporteTiempoUsoMonopatinDTO> getReporteUsoMonopatinesPorTiempo(boolean conPausas) {
        List<ScooterDTO> scooters = scooterClient.getScooters();
        List<ReporteTiempoUsoMonopatinDTO> resultadoReporte = new ArrayList<>();

        for (ScooterDTO scooter : scooters) {
            List<ViajeDTO> viajesScooter = viajesClient.getViajesByScooterId(scooter.getId());

            TiempoUsoMonopatinDTO cantTiempoUso = getCantTiempoUsoScooter(viajesScooter);

            ReporteTiempoUsoMonopatinDTO itemReporte =
                    new ReporteTiempoUsoMonopatinDTO(
                            scooter,
                            conPausas ? cantTiempoUso.getTiempoDeUsoConPausas() : cantTiempoUso.getTiempoDeUsoSinPausas()
                    );
            resultadoReporte.add(itemReporte);
        }

        return resultadoReporte;
    }

    public List<ReporteKilometrosMonopatinDTO> getReporteKilometrosPorMonopatin() {
        List<ScooterDTO> scooters = scooterClient.getScooters();
        List<ReporteKilometrosMonopatinDTO> resultadoReporte = new ArrayList<>();

        for (ScooterDTO scooter : scooters) {
            double cantKilometrosMonopatin = 0;
            List<ViajeDTO> viajesScooter = viajesClient.getViajesByScooterId(scooter.getId());

            for (ViajeDTO viaje : viajesScooter) {
                cantKilometrosMonopatin += viaje.getTotalKilometers();
            }

            ReporteKilometrosMonopatinDTO itemReporte =
                    new ReporteKilometrosMonopatinDTO(scooter, cantKilometrosMonopatin);

            resultadoReporte.add(itemReporte);
        }

        return resultadoReporte;
    }

    public CuentaDTO anularCuenta(long idCuenta) {
        return cuentaClient.disableAccount(idCuenta);
    }

    public List<ReporteScootersCantViajesDTO> getReporteScootersPorCantViajes(
            int cantViajesMinimos,
            LocalDateTime startDate,
            LocalDateTime endDate
    ) {
        List<ScooterDTO> scooters = scooterClient.getScooters();
        List<ReporteScootersCantViajesDTO> resultadoReporte = new ArrayList<>();

        for (ScooterDTO scooter : scooters) {
            List<ViajeDTO> viajesScooter = viajesClient.getViajesByScooterId(scooter.getId());
            long cantViajes =
                    viajesScooter.stream().filter(viaje ->
                            viaje.getStartTime().isAfter(startDate) &&
                                    viaje.getEndTime().isBefore(endDate == null ? LocalDateTime.now() : endDate)).count();

            if (cantViajes >= cantViajesMinimos) {
                ReporteScootersCantViajesDTO itemReporte = new ReporteScootersCantViajesDTO(scooter, cantViajes);
                resultadoReporte.add(itemReporte);
            }
        }

        return resultadoReporte;
    }

    public ReporteFacturacion getReporteTotalFacturado(LocalDateTime startDate, LocalDateTime endDate) {
        //Filtro los viajes por las fechas provistas
        double totalFacturado = 0;
        List<ViajeDTO> viajes = viajesClient.getViajes().stream()
                .filter(viajeDTO ->
                        (viajeDTO.getStartTime().isEqual(startDate) || viajeDTO.getStartTime().isAfter(startDate)) &&
                                (viajeDTO.getEndTime().isEqual(endDate) || viajeDTO.getEndTime().isBefore(endDate))
                )
                .collect(Collectors.toList());

        for(ViajeDTO viaje : viajes) {
            totalFacturado += viaje.getFinalPrice();
        }

        return new ReporteFacturacion(startDate, endDate, totalFacturado);
    }

    public ReporteCantidadMonopatines getReporteCantMonopatinesActivosYEnMantenimiento() {
        List <ScooterDTO> scootersDisponibles = scooterClient.getScooters(ScooterStatus.AVAILABLE);
        List <ScooterDTO> scootersMantenimiento = scooterClient.getScooters(ScooterStatus.UNDER_MAINTENANCE);
        long cantScootersDisponibles = scootersDisponibles.size();
        long cantScootersMantenimiento = scootersMantenimiento.size();

        return new ReporteCantidadMonopatines(cantScootersDisponibles, cantScootersMantenimiento);
    }

    private TiempoUsoMonopatinDTO getCantTiempoUsoScooter(List<ViajeDTO> viajesScooter) {
        Duration totalPausas = Duration.ZERO;
        Duration tiempoTotal = Duration.ZERO;
        Duration tiempoTotalConPausas;

        for (ViajeDTO viaje : viajesScooter) {
            Duration tiempoTotalViaje = Duration.between(viaje.getEndTime(), viaje.getStartTime());
            tiempoTotal.plus(tiempoTotalViaje);
            totalPausas.plus(viaje.getPausedTime());
        }

        tiempoTotalConPausas = tiempoTotal.plus(totalPausas);
        TiempoUsoMonopatinDTO tiemposMonopatin = new TiempoUsoMonopatinDTO(tiempoTotal, tiempoTotalConPausas);

        return tiemposMonopatin;
    }



}
