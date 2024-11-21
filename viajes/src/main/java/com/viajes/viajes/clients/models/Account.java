package com.viajes.viajes.clients.models;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor

public class Account {
    private Long id;
    private LocalDate fechaAlta;
    private double saldo;
    private String cuentaMP;
    private Boolean isDisable;

    public Account() {
    }
}
