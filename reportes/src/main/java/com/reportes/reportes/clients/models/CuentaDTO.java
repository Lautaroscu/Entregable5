package com.reportes.reportes.clients.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Set;

@NoArgsConstructor
@Getter
@Setter
public class CuentaDTO {
    private Long id;
    private LocalDate fechaAlta;
    private double saldo;
    private String cuentaMP;
    private Set<UserDTO> usuarios ;
}
