package com.reportes.reportes.DTOs;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReporteFacturacion {
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private BigDecimal cantFacturado;
}
