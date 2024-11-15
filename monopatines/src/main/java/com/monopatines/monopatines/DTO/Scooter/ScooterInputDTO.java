package com.monopatines.monopatines.DTO.Scooter;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
public class ScooterInputDTO implements Serializable {
    private double latitude;
    private double longitude;
    private String model;
}
