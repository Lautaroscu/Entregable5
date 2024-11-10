package com.reportes.reportes.DTOs;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Duration;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TiempoUsoMonopatinDTO {
    private Duration tiempoDeUsoSinPausas;
    private Duration tiempoDeUsoConPausas;
}
