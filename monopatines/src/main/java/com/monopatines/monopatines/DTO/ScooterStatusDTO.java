package com.monopatines.monopatines.DTO;

import com.monopatines.monopatines.Enumns.ScooterStatus;
import lombok.Getter;

import java.io.Serializable;
@Getter

public class ScooterStatusDTO  implements Serializable {
    private ScooterStatus scooterStatus;
    public ScooterStatusDTO() {}
    public ScooterStatusDTO(ScooterStatus scooterStatus) {
        this.scooterStatus = scooterStatus;
    }

}
