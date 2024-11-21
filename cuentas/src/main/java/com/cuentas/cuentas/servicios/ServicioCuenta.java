package com.cuentas.cuentas.servicios;

import com.cuentas.cuentas.DTO.*;
import com.cuentas.cuentas.entidades.Cuenta;
import com.cuentas.cuentas.entidades.Usuario;
import com.cuentas.cuentas.excepciones.AccountNotFoundException;
import com.cuentas.cuentas.repositorios.RepositorioCuenta;
import com.cuentas.cuentas.repositorios.RepositorioUsuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServicioCuenta {
    private final RepositorioCuenta cuentaRepositorio;
    private final RepositorioUsuario repositorioUsuario;

    @Autowired
    public ServicioCuenta(RepositorioCuenta cuentaRepositorio, RepositorioUsuario repositorioUsuario) {
        this.cuentaRepositorio = cuentaRepositorio;
        this.repositorioUsuario = repositorioUsuario;
    }

    public List<OutputCuentaDTO> getCuentas() {
        return cuentaRepositorio
                .findAll()
                .stream()
                .map(OutputCuentaDTO::new)
                .toList();
    }

    public OutputCuentaDTO crearCuenta(InputCuentaDTO inputCuentaDTO) {
        // Crear el nuevo Usuario
        Usuario usuario = new Usuario();
        usuario.setNombre(inputCuentaDTO.getNombreUsuario());
        usuario.setApellido(inputCuentaDTO.getApellidoUsuario());
        usuario.setEmail(inputCuentaDTO.getEmail());
        usuario.setCelular(inputCuentaDTO.getCelularUsuario());

        // Crear la nueva Cuenta
        Cuenta cuenta = new Cuenta();
        cuenta.setCuentaMercadoPago(inputCuentaDTO.getCtaMP());
        cuenta.setSaldo(inputCuentaDTO.getSaldo());
        cuenta.setEmailOwnerAccount(inputCuentaDTO.getEmail());
        cuenta.setPassword(inputCuentaDTO.getPassword());

        // Guardar primero la Cuenta
        cuentaRepositorio.save(cuenta);
        usuario.addCuenta(cuenta);  // Y también asociamos el usuario a la cuenta
        // Asociar la Cuenta con el Usuario
        cuenta.addUsuario(usuario);  // Aquí asociamos la cuenta con el usuario

        // Guardar luego el Usuario
        repositorioUsuario.save(usuario);
        cuenta = cuentaRepositorio.save(cuenta);
        return new OutputCuentaDTO(cuenta);
    }

    public OutputCuentaDTO getAccountById(Long id) {
        Cuenta cuenta = cuentaRepositorio.findById(id).orElseThrow(() -> new AccountNotFoundException("Account not found"));
        return new OutputCuentaDTO(cuenta);
    }

    public OutputCuentaDTO manageAvailability(AccountAvailabilityDTO accountAvailabilityDTO) {

        Cuenta cuenta = cuentaRepositorio.findById(accountAvailabilityDTO.getId()).orElseThrow(() -> new AccountNotFoundException("Account Not Found"));
        cuenta.setIsDisable(accountAvailabilityDTO.isAvailable());
        cuentaRepositorio.save(cuenta);
        return new OutputCuentaDTO(cuenta);
    }

    public OutputCuentaDTO setSaldo(Long id, AccountBalanceDTO saldo) {
        Cuenta cuenta = cuentaRepositorio.findById(id).orElseThrow(() -> new AccountNotFoundException("Account Not Found"));
        cuenta.setSaldo(saldo.getSaldo());
        cuentaRepositorio.save(cuenta);
        return new OutputCuentaDTO(cuenta);
    }

    public OutputCuentaDTO updateAccount(Long id, InputCuentaUpdateDTO inputCuentaDTO) {
        Cuenta cuenta = cuentaRepositorio.findById(id).orElseThrow(() -> new AccountNotFoundException("Account Not Found"));
        cuenta.setIsDisable(inputCuentaDTO.getIsDisable());
        cuenta.setSaldo(inputCuentaDTO.getSaldo());
        cuenta.setCuentaMercadoPago(inputCuentaDTO.getCuentaMP());
        cuentaRepositorio.save(cuenta);
        return new OutputCuentaDTO(cuenta);
    }

    public OutputCuentaDTO deleteAccount(Long id) {
        Cuenta cuenta = cuentaRepositorio.findById(id).orElseThrow(() -> new AccountNotFoundException("Account Not Found"));
        cuentaRepositorio.delete(cuenta);
        return new OutputCuentaDTO(cuenta);
    }

    public OutputCuentaDTO getByOwnerEmail(String ownerEmail) {
        Cuenta cuenta = cuentaRepositorio.findByEmailOwnerAccount(ownerEmail).orElseThrow(() -> new AccountNotFoundException("Account Not Found"));
        return new OutputCuentaDTO(cuenta);
    }

    public boolean avialableEmail(String email) {
        return !cuentaRepositorio.existsByEmailOwnerAccount(email);
    }
}
