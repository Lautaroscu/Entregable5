package com.reportes.reportes.entities;

import com.reportes.reportes.enums.TipoTarifa;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Entity
public class Tarifa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private TipoTarifa tipoTarifa;

    private BigDecimal monto;

    private String descripcion;


    public Tarifa() {}

    public Tarifa(Long id, TipoTarifa tipoTarifa, BigDecimal monto, String descripcion) {
        this.id = id;
        this.tipoTarifa = tipoTarifa;
        this.monto = monto;
        this.descripcion = descripcion;
    }

}
