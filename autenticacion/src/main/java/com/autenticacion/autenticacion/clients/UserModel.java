package com.autenticacion.autenticacion.clients;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserModel implements Serializable {
    private Long idUser;
    private String nombre;
    private String apellido;
    private double latitud;
    private double longitud;

}
