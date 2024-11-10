package com.reportes.reportes.clients.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class UserDTO {
    private Long idUser;
    private String nombre;
    private String apellido;
}
