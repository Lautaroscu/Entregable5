package repositories;

import com.cuentas.cuentas.entidades.Cuenta;
import com.cuentas.cuentas.repositorios.RepositorioCuenta;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


@SpringBootTest

public class CuentaRepositoryTest {
    private RepositorioCuenta repositorioCuenta;
    @Autowired
    public CuentaRepositoryTest(RepositorioCuenta repositorioCuenta) {
        this.repositorioCuenta = repositorioCuenta;
    }



    @Test
    void testFindByOwnerEmailWorks() {
        Cuenta cuenta = new Cuenta();
        cuenta.setCuentaMercadoPago("ct1");
        cuenta.setCuentaMercadoPago("mailtest@mail.com");

        repositorioCuenta.save(cuenta);
        Cuenta cuantaFound = repositorioCuenta.findByEmailOwnerAccount("mailtest@mail.com").get();
        assertNotNull(cuenta);
        assertEquals(cuenta.getIdCuenta(), cuantaFound.getIdCuenta());

        repositorioCuenta.delete(cuenta);

    }

}