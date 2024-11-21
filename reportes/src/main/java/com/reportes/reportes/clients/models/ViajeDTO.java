package com.reportes.reportes.clients.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ViajeDTO implements Serializable {
    private String tripID;
    private ScooterDTO scooterDTO;
    private Account account;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private double finalPrice;
    private TripStatus tripStatus;
    private double kilometersTraveled;
    private LocalDateTime startPauseTime;
    private LocalDateTime endPauseTime;
}
