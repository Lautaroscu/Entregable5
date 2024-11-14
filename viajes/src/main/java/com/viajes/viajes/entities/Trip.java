package com.viajes.viajes.entities;

import com.viajes.viajes.clients.models.Account;
import com.viajes.viajes.clients.models.ScooterDTO;
import com.viajes.viajes.enumns.TripStatus;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;

@Getter
@Setter
@Document(collection = "trips")
public class Trip {
    @Id
    private String tripID;
    private ScooterDTO scooterDTO;
    private Account account;
    private TripStatus tripStatus;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private double kilometersTraveled;
    private LocalDateTime startPauseTime;
    private LocalDateTime endPauseTime;
    private Duration pausedTime;
    private BigDecimal finalPrice;
    private BigDecimal currentPrice;

    public Trip() {
    }

    public Trip(ScooterDTO scooterDTO, Account account) {
        this.scooterDTO = scooterDTO;
        this.account = account;
        this.tripStatus = TripStatus.STARTED;
        this.startTime = LocalDateTime.now();
        this.endTime = null;
        this.kilometersTraveled = 0.0;
        this.pausedTime = Duration.ZERO;
        this.finalPrice = new BigDecimal(0);
        this.currentPrice = new BigDecimal(0);
    }

    public void setCurrentPrice(BigDecimal monto) {
        currentPrice = currentPrice.add(monto);
    }
}
