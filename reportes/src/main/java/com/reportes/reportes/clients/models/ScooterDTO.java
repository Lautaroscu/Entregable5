package com.reportes.reportes.clients.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ScooterDTO {
   private long id;
    private String location;
    private String model;
   private String status;
}
