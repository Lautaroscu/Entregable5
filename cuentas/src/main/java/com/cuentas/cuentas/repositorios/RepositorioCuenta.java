package com.cuentas.cuentas.repositorios;

import com.cuentas.cuentas.entidades.Cuenta;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RepositorioCuenta extends JpaRepository<Cuenta, Long> {
}
