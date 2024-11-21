package com.viajes.viajes.services;

import com.viajes.viajes.DTO.TripInputDTO;
import com.viajes.viajes.DTO.TripOutputDTO;
import com.viajes.viajes.clients.AccountClient;
import com.viajes.viajes.clients.ScooterClient;
import com.viajes.viajes.clients.TarifasClient;
import com.viajes.viajes.clients.models.Account;
import com.viajes.viajes.clients.models.InputCuentaUpdateDTO;
import com.viajes.viajes.clients.models.ScooterDTO;
import com.viajes.viajes.clients.models.TarifaDTO;
import com.viajes.viajes.entities.Trip;
import com.viajes.viajes.enumns.TripStatus;
import com.viajes.viajes.exceptions.TripNotFoundException;
import com.viajes.viajes.repositories.TripRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class TripService {
    private final TripRepository tripRepository;
    private final AccountClient accountClient;
    private final ScooterClient scooterClient;
    private final TarifasClient tarifasClient;

    @Autowired
    public TripService(TripRepository tripRepository, AccountClient accountClient, ScooterClient scooterClient, TarifasClient tarifasClient) {
        this.tripRepository = tripRepository;
        this.accountClient = accountClient;
        this.scooterClient = scooterClient;
        this.tarifasClient = tarifasClient;
    }

    public TripOutputDTO createTrip(TripInputDTO tripInputDTO) {
        Account account = accountClient.getAccountById(tripInputDTO.getAccountId());
        ScooterDTO scooter = scooterClient.getScooterById(tripInputDTO.getScooterId());
        Trip trip = new Trip(scooter, account);
        tripRepository.save(trip);
        return new TripOutputDTO(trip);
    }

    public List<TripOutputDTO> getAllTrips() {
        return tripRepository
                .findAll()
                .stream()
                .map(TripOutputDTO::new)
                .toList();
    }

    public TripOutputDTO setPauseTrip(String tripID) {
        Trip trip = tripRepository.findById(tripID).orElseThrow(() -> new TripNotFoundException("Trip not found"));
        trip.setTripStatus(TripStatus.PAUSED);
        trip.setStartPauseTime(LocalDateTime.now());
        tripRepository.save(trip);
        return new TripOutputDTO(trip);
    }

    public TripOutputDTO setUnPauseTrip(String tripID) {
        Trip trip = tripRepository.findById(tripID).orElseThrow(() -> new TripNotFoundException("Trip not found"));
        trip.setTripStatus(TripStatus.STARTED);
        trip.setEndPauseTime(LocalDateTime.now());
        tripRepository.save(trip);
        return new TripOutputDTO(trip);
    }

    public List<TripOutputDTO> getAllByScooterID(String scooterID) {

        return tripRepository.findByScooterID(scooterID).stream().map(TripOutputDTO::new).toList();
    }

    public TripOutputDTO updatePrice(String tripID, String tipoTarifa) {

        TarifaDTO tarifa = tarifasClient.getTarifaByTipo(tipoTarifa);
        BigDecimal montoTarifa = tarifa.getMonto();
        Trip trip = tripRepository.findById(tripID).orElseThrow(() -> new TripNotFoundException("Trip not found"));
        trip.setCurrentPrice(montoTarifa);


        Account tripAccount = trip.getAccount();
        double nuevoSaldo = tripAccount.getSaldo() - montoTarifa.doubleValue();
        Account accountModified = accountClient.updateAccount(tripAccount.getId(), new InputCuentaUpdateDTO(nuevoSaldo, tripAccount.getCuentaMP(), tripAccount.getIsDisable()));
        trip.setAccount(accountModified);

        tripRepository.save(trip);
        return new TripOutputDTO(trip);
    }

    public TripOutputDTO EndTrip(String tripID) {
        Trip trip = tripRepository.findById(tripID).orElseThrow(() -> new TripNotFoundException("Trip not found"));
        trip.setTripStatus(TripStatus.COMPLETED);
        trip.setFinalPrice(trip.getCurrentPrice());
        trip.setEndTime(LocalDateTime.now());
        tripRepository.save(trip);

        return new TripOutputDTO(trip);
    }
}
