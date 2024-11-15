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
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class TripOutputDTO implements Serializable {
    private String tripID;
    private ScooterDTO scooterDTO;
    private Account account;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private BigDecimal price;
    private TripStatus tripStatus;
    private double kilometersTraveled;
    private LocalDateTime startPauseTime;
    private LocalDateTime endPauseTime;

    public TripOutputDTO(Trip trip) {
        this.tripID = trip.getTripID();
        this.scooterDTO = trip.getScooterDTO();
        this.account = trip.getAccount();
        this.startTime = trip.getStartTime();
        this.endTime = trip.getEndTime();
        this.tripStatus = trip.getTripStatus();
        this.startPauseTime = trip.getStartPauseTime();
        this.endPauseTime = trip.getEndPauseTime();
        this.price = trip.getCurrentPrice();
    }
}
