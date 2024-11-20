package com.cuentas.cuentas.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class AccountBalanceDTO implements Serializable {
    private double saldo;
}
