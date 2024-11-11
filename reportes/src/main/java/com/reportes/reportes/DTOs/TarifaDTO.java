package com.reportes.reportes.DTOs;

import com.reportes.reportes.entities.Tarifa;
import com.reportes.reportes.enums.TipoTarifa;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TarifaDTO {
    private Long id;
    private TipoTarifa tipoTarifa;
    private BigDecimal monto;
    private String descripcion;

    public TarifaDTO(Tarifa tarifa) {
        this.id = tarifa.getId();
        this.tipoTarifa = tarifa.getTipoTarifa();
        this.monto = tarifa.getMonto();
        this.descripcion = tarifa.getDescripcion();
    }
}