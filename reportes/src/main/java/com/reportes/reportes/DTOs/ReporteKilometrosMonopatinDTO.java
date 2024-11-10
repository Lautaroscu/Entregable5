package com.reportes.reportes.DTOs;

import com.reportes.reportes.clients.models.ScooterDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReporteKilometrosMonopatinDTO {
    private ScooterDTO scooterDTO;
    private double cantKilometros;
}
