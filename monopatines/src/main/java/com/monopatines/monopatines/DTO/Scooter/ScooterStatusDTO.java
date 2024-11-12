package com.monopatines.monopatines.DTO.Scooter;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.monopatines.monopatines.Enumns.ScooterStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ScooterStatusDTO implements Serializable {
    @JsonProperty("status")
    private ScooterStatus scooterStatus;
}
