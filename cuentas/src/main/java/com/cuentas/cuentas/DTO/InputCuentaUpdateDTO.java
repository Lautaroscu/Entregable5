package com.cuentas.cuentas.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class InputCuentaUpdateDTO implements Serializable {
    private double saldo;
    private String cuentaMP;
    private Boolean isDisable;
}
