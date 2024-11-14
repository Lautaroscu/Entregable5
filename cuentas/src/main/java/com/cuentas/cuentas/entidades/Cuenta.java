package com.cuentas.cuentas.entidades;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "cuenta")
@Getter
@Setter
@ToString
public class Cuenta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCuenta;

    @Column(nullable = false)
    private LocalDate fechaAlta;

    @Column(nullable = false)
    @PositiveOrZero
    private Double saldo;

    @Column(nullable = false)
    private String cuentaMercadoPago;

    private Boolean isDisable;

    // Relación muchos a muchos con Usuario
    @ManyToMany
    @JoinTable(
            name = "cuenta_usuario",
            joinColumns = @JoinColumn(name = "idCuenta"),
            inverseJoinColumns = @JoinColumn(name = "idUsuario")
    )
    @JsonManagedReference
    private Set<Usuario> usuarios;

    public Cuenta() {
        this.usuarios = new HashSet<>();
        this.fechaAlta = LocalDate.now();
        this.saldo = 0.0;
        this.cuentaMercadoPago = "";
        this.isDisable = false;
    }

    public Cuenta(Double saldo, String cuentaMercadoPago) {
        this.fechaAlta = LocalDate.now();
        this.saldo = saldo;
        this.cuentaMercadoPago = cuentaMercadoPago;
        this.usuarios = new HashSet<>();
        this.isDisable = false;
    }

    public void addUsuario(Usuario usuario) {
        this.usuarios.add(usuario);
    }
}
