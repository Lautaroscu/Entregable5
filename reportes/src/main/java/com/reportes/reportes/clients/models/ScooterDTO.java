package com.reportes.reportes.clients.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ScooterDTO {
    private String id;
    private ScooterStatus status;
    private String location;
    private double kilometersTraveled;
    private double totalTimeUsed;
}
