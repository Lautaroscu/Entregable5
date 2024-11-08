package com.viajes.viajes.DTO;

import com.viajes.viajes.clients.models.Account;
import com.viajes.viajes.clients.models.ScooterDTO;
import com.viajes.viajes.entities.Trip;
import com.viajes.viajes.enumns.TripStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
@Getter
@Setter
@NoArgsConstructor
public class TripOutputDTO implements Serializable {
    private ScooterDTO scooterDTO;
    private Account account;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private double finalPrice;
    private TripStatus tripStatus;
    public TripOutputDTO(Trip trip) {
        this.scooterDTO = trip.getScooterDTO();
        this.account = trip.getAccount();
        this.startTime = trip.getStartTime();
        this.endTime = trip.getEndTime();
        this.tripStatus = trip.getTripStatus();

    }

}
