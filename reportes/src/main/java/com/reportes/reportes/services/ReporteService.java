package com.reportes.reportes.services;

import com.reportes.reportes.DTOs.ReporteKilometrosMonopatinDTO;
import com.reportes.reportes.DTOs.ReporteTiempoUsoMonopatinDTO;
import com.reportes.reportes.DTOs.TiempoUsoMonopatinDTO;
import com.reportes.reportes.clients.ScooterClient;
import com.reportes.reportes.clients.ViajesClient;
import com.reportes.reportes.clients.models.ScooterDTO;
import com.reportes.reportes.clients.models.TripDTO;
import com.reportes.reportes.repositories.ReporteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

@Service
public class ReporteService {
    private final ReporteRepository reporteRepository;
    private final ViajesClient viajesClient;
    private final ScooterClient scooterClient;

    @Autowired
    public ReporteService(ReporteService reporteService, ViajesClient viajesClient, ScooterClient scooterClient) {
        this.reporteRepository = reporteService.reporteRepository;
        this.viajesClient = viajesClient;
        this.scooterClient = scooterClient;
    }

    public List<ReporteTiempoUsoMonopatinDTO> getReporteUsoMonopatinesPorTiempo(boolean conPausas) {
        List<ScooterDTO> scooters = scooterClient.getScooters();
        List<ReporteTiempoUsoMonopatinDTO> resultadoReporte = new ArrayList<>();

        for (ScooterDTO scooter : scooters) {
            List<TripDTO> viajesScooter = viajesClient.getViajesByScooterId(scooter.getId());

            TiempoUsoMonopatinDTO cantTiempoUso = getCantTiempoUsoScooter(scooter, viajesScooter);

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
            List<TripDTO> viajesScooter = viajesClient.getViajesByScooterId(scooter.getId());

            for (TripDTO viaje : viajesScooter) {
                cantKilometrosMonopatin += viaje.getTotalKilometers();
            }

            ReporteKilometrosMonopatinDTO itemReporte =
                    new ReporteKilometrosMonopatinDTO(scooter, cantKilometrosMonopatin);

            resultadoReporte.add(itemReporte);
        }

        return resultadoReporte;
    }

    private TiempoUsoMonopatinDTO getCantTiempoUsoScooter(ScooterDTO scooter, List<TripDTO> viajesScooter) {
        Duration totalPausas = Duration.ZERO;
        Duration tiempoTotal = Duration.ZERO;
        Duration tiempoTotalConPausas;

        for (TripDTO viaje : viajesScooter) {
            Duration tiempoTotalViaje = Duration.between(viaje.getEndTime(), viaje.getStartTime());
            tiempoTotal.plus(tiempoTotalViaje);
            totalPausas.plus(viaje.getPausedTime());
        }

        tiempoTotalConPausas = tiempoTotal.plus(totalPausas);
        TiempoUsoMonopatinDTO tiemposMonopatin = new TiempoUsoMonopatinDTO(tiempoTotal, tiempoTotalConPausas);

        return tiemposMonopatin;
    }
}
