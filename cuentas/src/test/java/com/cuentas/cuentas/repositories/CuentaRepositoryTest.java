package com.cuentas.cuentas.repositories;

import com.cuentas.cuentas.entidades.Cuenta;
import com.cuentas.cuentas.repositorios.RepositorioCuenta;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


@SpringBootTest
public class CuentaRepositoryTest {

    @Autowired
    private RepositorioCuenta repositorioCuenta;

    @Test
    void testFindByOwnerEmailWorks() {
        Cuenta cuenta = new Cuenta();
        cuenta.setCuentaMercadoPago("ct1");
        cuenta.setEmailOwnerAccount("mailtest@mail.com");
        repositorioCuenta.save(cuenta);

        Cuenta cuentaFound = repositorioCuenta.findByEmailOwnerAccount("mailtest@mail.com").get();

        assertNotNull(cuenta);
        assertEquals(cuenta.getIdCuenta(), cuentaFound.getIdCuenta());

        repositorioCuenta.delete(cuenta);
    }
}