package com.reportes.reportes.services;

import com.reportes.reportes.DTOs.*;
import com.reportes.reportes.clients.ScooterClient;
import com.reportes.reportes.clients.ViajesClient;
import com.reportes.reportes.clients.models.*;
import com.reportes.reportes.entities.Tarifa;
import com.reportes.reportes.repositories.ReporteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class ReporteService {
    private final ReporteRepository reporteRepository;
    private final ViajesClient viajesClient;
    private final ScooterClient scooterClient;

    @Autowired
    public ReporteService(
            ReporteRepository reporteRepository ,
            ViajesClient viajesClient,
            ScooterClient scooterClient
    ) {
        this.reporteRepository = reporteRepository;
        this.viajesClient = viajesClient;
        this.scooterClient = scooterClient;
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
                cantKilometrosMonopatin += viaje.getKilometersTraveled();
            }

            ReporteKilometrosMonopatinDTO itemReporte =
                    new ReporteKilometrosMonopatinDTO(scooter, cantKilometrosMonopatin);

            resultadoReporte.add(itemReporte);
        }

        return resultadoReporte;
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
                .toList();

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

    public List<TarifaDTO> getTarifas() {
        return reporteRepository.findAll().stream().map(TarifaDTO::new).toList();
    }

    public List<TarifaDTO> upsertTarifa(List<TarifaDTO> tarifas) {
        List<TarifaDTO> tarifasModificadas = new ArrayList<>();
        for(TarifaDTO tarifa : tarifas){
            Tarifa t = new Tarifa(tarifa.getId(), tarifa.getTipoTarifa(), tarifa.getMonto(),  tarifa.getDescripcion());
            Tarifa tarifaModificada = reporteRepository.save(t);
            tarifasModificadas.add(new TarifaDTO(tarifaModificada));
        }

        return tarifasModificadas;
    }

    private TiempoUsoMonopatinDTO getCantTiempoUsoScooter(List<ViajeDTO> viajesScooter) {
        Duration totalPausas = Duration.ZERO;
        Duration tiempoTotal = Duration.ZERO;
        Duration tiempoTotalConPausas;

        for (ViajeDTO viaje : viajesScooter) {
            if(viaje.getTripStatus() == TripStatus.COMPLETED){
                Duration tiempoTotalViaje = Duration.between(viaje.getEndTime(), viaje.getStartTime());
                tiempoTotal.plus(tiempoTotalViaje);
                totalPausas.plus(Duration.between(viaje.getEndPauseTime(), viaje.getStartPauseTime()));
            }
        }

        tiempoTotalConPausas = tiempoTotal.plus(totalPausas);
        return new TiempoUsoMonopatinDTO(tiempoTotal, tiempoTotalConPausas);
    }

    public TarifaDTO getTarifaPorTipo(String tipo) {
        Tarifa response = reporteRepository.findByTipoTarifa(tipo);
        return new TarifaDTO(response);
    }
}
