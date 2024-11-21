package com.monopatines.monopatines.DTO.Scooter;


import com.monopatines.monopatines.Enumns.ScooterStatus;
import com.monopatines.monopatines.entities.Scooter;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class ScooterOutputDTO extends ScooterInputDTO implements Serializable {
    private String id;
    private ScooterStatus status;
    private double kilometersTraveled;
    private double totalTimeUsed;
    private double latitude;
    private double longitude;

    public ScooterOutputDTO(Scooter scooter) {
        super(scooter.getLatitude(), scooter.getLongitude(), scooter.getModel());
        this.id = scooter.getId();
        this.status = scooter.getStatus();
        this.totalTimeUsed = scooter.getTotalTimeUsed();
        this.kilometersTraveled = scooter.getKilometersTraveled();
        this.latitude = scooter.getLatitude();
        this.longitude = scooter.getLongitude();
    }
}
