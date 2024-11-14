package com.viajes.viajes.clients.models;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class TarifaDTO {
    private Long id;
    private String tipoTarifa;
    private BigDecimal monto;
    private String descripcion;
}
