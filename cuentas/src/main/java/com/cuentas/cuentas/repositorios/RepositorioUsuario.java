package com.cuentas.cuentas.repositorios;

import com.cuentas.cuentas.entidades.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RepositorioUsuario extends JpaRepository<Usuario, Long> {

}
