package com.reportes.reportes.entities;

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
    private String tipoTarifa;

    private BigDecimal monto;

    private String descripcion;

    public Tarifa() {
    }

    public Tarifa(Long id, String tipoTarifa, BigDecimal monto, String descripcion) {
        this.id = id;
        this.tipoTarifa = tipoTarifa;
        this.monto = monto;
        this.descripcion = descripcion;
    }
}
