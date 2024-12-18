package com.cuentas.cuentas.DTO;

import com.cuentas.cuentas.entidades.Usuario;
import lombok.Getter;

import java.io.Serializable;

@Getter
public class OutputUserDTO implements Serializable {
    private Long idUser;
    private String nombre;
    private String apellido;
    private double latitud;
    private double longitud;

    public OutputUserDTO() {
    }

    public OutputUserDTO(Usuario usuario) {
        this.idUser = usuario.getIdUsuario();
        this.nombre = usuario.getNombre();
        this.apellido = usuario.getApellido();
        this.latitud = usuario.getLatitud();
        this.longitud = usuario.getLongitud();
    }
}
