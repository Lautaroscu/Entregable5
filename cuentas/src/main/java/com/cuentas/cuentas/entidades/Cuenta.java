package com.cuentas.cuentas.entidades;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "cuenta")
@Getter
@Setter
public class Cuenta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCuenta;  // Número identificatorio de la cuenta

    @Column(nullable = false)
    private LocalDate fechaAlta;

    @Column(nullable = false)
    private Double saldo;

    @Column(nullable = false)
    private String cuentaMercadoPago;  // ID de la cuenta de Mercado Pago asociada

    // Relación muchos a muchos con Usuario
    @ManyToMany
    @JoinTable(
            name = "cuenta_usuario",  // Cambiar el nombre de la tabla intermedia
            joinColumns = @JoinColumn(name = "idCuenta"),
            inverseJoinColumns = @JoinColumn(name = "idUsuario")
    )
    private Set<Usuario> usuarios;

    public Cuenta() {
        this.usuarios = new HashSet<>();
        this.fechaAlta = LocalDate.now();
    }

    public Cuenta(Double saldo, String cuentaMercadoPago) {
        this.fechaAlta = LocalDate.now();
        this.saldo = saldo;
        this.cuentaMercadoPago = cuentaMercadoPago;
        this.usuarios = new HashSet<>();
    }

    public void addUsuario(Usuario usuario) {
        this.usuarios.add(usuario);
    }
}
