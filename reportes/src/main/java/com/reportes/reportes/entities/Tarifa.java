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
}
