package com.cuentas.cuentas.entidades;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "usuario")
@Getter
@Setter
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idUsuario;

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
    @JsonBackReference  // Esta anotación previene la recursión en la parte inversa de la relación
    private Set<Cuenta> cuentas;

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

    @Override
    public String toString() {
        return apellido + " " + nombre + " " + celular + " " + email;
    }
}
