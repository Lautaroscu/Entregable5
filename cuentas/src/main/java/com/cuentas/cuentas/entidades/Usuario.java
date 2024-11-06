package com.cuentas.cuentas.entidades;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "usuario")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idUsuario;  // ID único del usuario

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private String apellido;

    @Column(nullable = false, unique = true)
    private String celular;

    @Column(nullable = false, unique = true)
    private String email;

    // Relación muchos a muchos con Cuenta
    @ManyToMany(mappedBy = "usuarios")
    private Set<Cuenta> cuentas;

    // Getters y Setters
    public Usuario() {
        this.cuentas = new HashSet<>();
    }
    public Usuario(String nombre, String apellido, String celular, String email) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.celular = celular;
        this.email = email;
        cuentas = new HashSet<>();
    }
    public void addCuenta(Cuenta cuenta) {
        cuentas.add(cuenta);
    }

}
