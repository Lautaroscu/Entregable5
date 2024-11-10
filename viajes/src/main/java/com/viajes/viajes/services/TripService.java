package com.viajes.viajes.services;

import com.viajes.viajes.DTO.TripInputDTO;
import com.viajes.viajes.DTO.TripOutputDTO;
import com.viajes.viajes.clients.AccountClient;
import com.viajes.viajes.clients.ScooterClient;
import com.viajes.viajes.clients.models.Account;
import com.viajes.viajes.clients.models.ScooterDTO;
import com.viajes.viajes.entities.Trip;
import com.viajes.viajes.repositories.TripRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TripService {
    private final TripRepository tripRepository;
    private final AccountClient accountClient;
    private final ScooterClient scooterClient;
    @Autowired
    public TripService(TripRepository tripRepository, AccountClient accountClient, ScooterClient scooterClient) {
        this.tripRepository = tripRepository;
        this.accountClient = accountClient;
        this.scooterClient = scooterClient;
    }
    public void createTrip(TripInputDTO tripInputDTO) {
        Account account = accountClient.getAccountById(tripInputDTO.getAccountId());
        ScooterDTO scooter = scooterClient.getScooterById(tripInputDTO.getScooterId());
        Trip trip = new Trip(scooter , account);
        tripRepository.save(trip);

    }
    public List<TripOutputDTO> getAllTrips() {
        return tripRepository
                .findAll()
                .stream()
                .map(TripOutputDTO::new)
                .toList();
    }
}
