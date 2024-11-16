package com.cuentas.cuentas.DTO;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class InputCuentaDTO implements Serializable {
    private String ctaMP;
    private String nombreUsuario;
    private String apellidoUsuario;
    private String celularUsuario;
    private String email;
    private String password;
    private Double saldo;
    private Boolean isDisable;

    public InputCuentaDTO(
            String ctaMP,
            String nombreUsuario,
            String apellidoUsuario,
            String celularUsuario,
            String email,
            Double saldo ,
            Boolean isDisable ,
            String password
    ) {
        this.ctaMP = ctaMP;
        this.nombreUsuario = nombreUsuario;
        this.apellidoUsuario = apellidoUsuario;
        this.celularUsuario = celularUsuario;
        this.email = email;
        this.saldo = saldo;
        this.isDisable = isDisable;
        this.password = password;
    }
}
