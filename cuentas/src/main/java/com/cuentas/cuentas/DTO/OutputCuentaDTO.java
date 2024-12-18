package com.cuentas.cuentas.DTO;

import com.cuentas.cuentas.entidades.Cuenta;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;
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
    private String ownerEmail;
    private String password;
    private Set<OutputUserDTO> usuarios;

    public OutputCuentaDTO(Cuenta cuenta) {
        this.id = cuenta.getIdCuenta();
        this.fechaAlta = cuenta.getFechaAlta();
        this.saldo = cuenta.getSaldo();
        this.cuentaMP = cuenta.getCuentaMercadoPago();
        this.ownerEmail = cuenta.getEmailOwnerAccount();
        this.password = cuenta.getPassword();
        this.usuarios = cuenta.getUsuarios().stream().map(OutputUserDTO::new).collect(Collectors.toSet());
        this.isDisable = cuenta.getIsDisable();

    }
}
