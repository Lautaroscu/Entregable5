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

public ScooterOutputDTO(Scooter scooter) {
    super(scooter.getLocation() ,scooter.getModel());
    this.id = scooter.getId();
    this.status = scooter.getStatus();
}



}
