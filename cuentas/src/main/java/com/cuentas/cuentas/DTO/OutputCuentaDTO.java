package com.cuentas.cuentas.DTO;

import com.cuentas.cuentas.entidades.Cuenta;
import com.cuentas.cuentas.entidades.Usuario;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@NoArgsConstructor
@Getter
@Setter
public class OutputCuentaDTO implements Serializable {
    private Long id;
    private LocalDate fechaAlta;
    private double saldo;
    private String cuentaMP;
    private Boolean isDisable;
    private Set<OutputUserDTO> usuarios;

    public OutputCuentaDTO(Cuenta cuenta) {
        this.id = cuenta.getIdCuenta();
        this.fechaAlta = cuenta.getFechaAlta();
        this.saldo = cuenta.getSaldo();
        this.cuentaMP = cuenta.getCuentaMercadoPago();
        if(!cuenta.getUsuarios().isEmpty())
        this.usuarios = cuenta.getUsuarios().stream().map(OutputUserDTO::new).collect(Collectors.toSet());
        this.isDisable = cuenta.getIsDisable();
    }
}
