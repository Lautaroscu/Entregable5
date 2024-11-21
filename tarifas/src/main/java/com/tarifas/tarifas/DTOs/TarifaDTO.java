package com.tarifas.tarifas.DTOs;

import com.tarifas.tarifas.entities.Tarifa;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "DTO que representa la información de una tarifa")
public class TarifaDTO {

    @Schema(description = "Identificador único de la tarifa", example = "1")
    private Long id;

    @Schema(description = "Tipo de tarifa", example = "Premium")
    private String tipoTarifa;

    @Schema(description = "Monto de la tarifa", example = "19.99")
    private BigDecimal monto;

    @Schema(description = "Descripción de la tarifa", example = "Tarifa mensual premium")
    private String descripcion;

    // Constructor, getters and setters
    public TarifaDTO(Tarifa tarifa) {
        this.id = tarifa.getId();
        this.tipoTarifa = tarifa.getTipoTarifa();
        this.monto = tarifa.getMonto();
        this.descripcion = tarifa.getDescripcion();
    }
}

