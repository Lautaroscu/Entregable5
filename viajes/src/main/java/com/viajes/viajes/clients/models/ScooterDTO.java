package com.viajes.viajes.clients.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ScooterDTO {
    private String id;
    private String status;
    private double kilometersTraveled;
    private double totalTimeUsed;
    private double latitude;
    private double longitude;
}
