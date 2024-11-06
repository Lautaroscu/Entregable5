package com.cuentas.cuentas.DTO;

import com.cuentas.cuentas.entidades.Cuenta;
import com.cuentas.cuentas.entidades.Usuario;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

public class OutputCuentaDTO implements Serializable {
    private Long id;
    private LocalDate fechaAlta;
    private Set<Usuario> usuarios;

    public OutputCuentaDTO(Cuenta cuenta) {
        this.id = cuenta.getIdCuenta();
        this.fechaAlta = cuenta.getFechaAlta();
        this.usuarios = new HashSet<>();
        usuarios.addAll(cuenta.getUsuarios());

    }

}
