package com.viajes.viajes.clients.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class InputCuentaUpdateDTO {
    private double saldo;
    private String cuentaMP;
    private Boolean isDisable;
}