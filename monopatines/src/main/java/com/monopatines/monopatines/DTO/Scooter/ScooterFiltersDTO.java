package com.monopatines.monopatines.DTO.Scooter;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ScooterFiltersDTO {
    private String location;
    private String status;
    private String model;

    public ScooterFiltersDTO(String location, String status, String model) {
        this.location = location;
        this.status = status;
        this.model = model;
    }
}
