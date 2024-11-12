package com.cuentas.cuentas.repositorios;

import com.cuentas.cuentas.entidades.Cuenta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RepositorioCuenta extends JpaRepository<Cuenta, Long> {

}
