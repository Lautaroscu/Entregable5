package com.reportes.reportes.clients.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.Duration;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class TripDTO implements Serializable {
    private ScooterDTO scooterDTO;
    private Account account;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Duration pausedTime;
    private double finalPrice;
    private double totalKilometers;
    private TripStatus tripStatus;
}
