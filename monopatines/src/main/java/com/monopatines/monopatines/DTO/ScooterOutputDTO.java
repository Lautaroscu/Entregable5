package com.monopatines.monopatines.DTO;


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
    private String location;
    private double kilometersTraveled;
    private double totalTimeUsed;

public ScooterOutputDTO(Scooter scooter) {
    super(scooter.getLocation() ,scooter.getModel());
    this.id = scooter.getId();
    this.status = scooter.getStatus();
    this.location = scooter.getLocation();
    this.totalTimeUsed = scooter.getTotalTimeUsed();
    this.kilometersTraveled = scooter.getKilometersTraveled();
}



}
