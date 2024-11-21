package com.autenticacion.autenticacion.DTO;

import lombok.Data;

import java.io.Serializable;

@Data
public class LoginInputDTO implements Serializable {
    private String email;
    private String password;
}
