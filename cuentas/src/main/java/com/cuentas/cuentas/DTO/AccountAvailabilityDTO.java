package com.cuentas.cuentas.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
public class AccountAvailabilityDTO implements Serializable {
    private Long id;
    private boolean isAvailable;
}
