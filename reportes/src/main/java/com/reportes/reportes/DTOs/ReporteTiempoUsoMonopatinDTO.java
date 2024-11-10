package com.reportes.reportes.DTOs;

import com.reportes.reportes.clients.models.ScooterDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Duration;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReporteTiempoUsoMonopatinDTO {
    private ScooterDTO scooterDTO;
    private Duration tiempoDeUso;
}
