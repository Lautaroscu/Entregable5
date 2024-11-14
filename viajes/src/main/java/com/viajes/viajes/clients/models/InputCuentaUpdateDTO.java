package com.viajes.viajes.clients.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class InputCuentaUpdateDTO  {
    private double saldo;
    private String cuentaMP;
    private Boolean isDisable;
}