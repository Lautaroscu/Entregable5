package com.autenticacion.autenticacion.clients;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountModel implements Serializable {
    private Long id;
    private LocalDate fechaAlta;
    private double saldo;
    private String cuentaMP;
    private Boolean isDisable;
    private String ownerEmail;
    private String password;
    private Set<UserModel> usuarios;


}
