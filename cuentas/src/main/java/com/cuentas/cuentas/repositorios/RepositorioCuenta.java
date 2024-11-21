package com.cuentas.cuentas.repositorios;

import com.cuentas.cuentas.entidades.Cuenta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RepositorioCuenta extends JpaRepository<Cuenta, Long> {
    Optional<Cuenta> findByEmailOwnerAccount(String emailOwnerAccount);

    Boolean existsByEmailOwnerAccount(String emailOwnerAccount);
}
