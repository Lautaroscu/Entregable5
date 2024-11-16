package com.autenticacion.autenticacion.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterInputDTO {
    private String ctaMP;
    private String nombreUsuario;
    private String apellidoUsuario;
    private String celularUsuario;
    private String email;
    private String password;
    private String confirmPassword;
    private Double saldo;
    private Boolean isDisable;
}
