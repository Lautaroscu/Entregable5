package services;

import com.cuentas.cuentas.DTO.*;
import com.cuentas.cuentas.entidades.Cuenta;
import com.cuentas.cuentas.repositorios.RepositorioCuenta;
import com.cuentas.cuentas.servicios.ServicioCuenta;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

public class CuentaServiceTest {
    @Mock
    private RepositorioCuenta repositorioCuenta;
    @InjectMocks
    private ServicioCuenta servicioCuenta;
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetCuentas() {
        Cuenta expected = this.getMockedCuenta();
        List<Cuenta> cuentas = List.of(expected);
        when(repositorioCuenta.findAll()).thenReturn(cuentas);
        List<OutputCuentaDTO> result = servicioCuenta.getCuentas();
        assertNotNull(result);
        assertEquals(cuentas.size(), result.size());


    }
    @Test
    void testGetCuentasById() {
        Cuenta expected = this.getMockedCuenta();
        when(repositorioCuenta.findById(expected.getIdCuenta())).thenReturn(Optional.of(expected));
        OutputCuentaDTO result = servicioCuenta.getAccountById(1L);
        assertNotNull(result);
        assertEquals(expected.getIdCuenta() , result.getId());
    }
    @Test
    void testManageAvailability() {
        AccountAvailabilityDTO accountAvailabilityDTO = new AccountAvailabilityDTO(1L , true);
        OutputCuentaDTO dto = getMockedCuentaDTO();
        when(repositorioCuenta.findById(accountAvailabilityDTO.getId())).thenReturn(Optional.of(getMockedCuenta()));
        OutputCuentaDTO result = servicioCuenta.manageAvailability(accountAvailabilityDTO);
        assertNotNull(result);
        assertNotEquals(dto.getIsDisable() , result.getIsDisable());
    }
    @Test
    void testSetSaldo() {
        AccountBalanceDTO accountBalanceDTO = new AccountBalanceDTO(9000);
        OutputCuentaDTO dto = getMockedCuentaDTO();
        when(repositorioCuenta.findById(1L)).thenReturn(Optional.of(getMockedCuenta()));
        OutputCuentaDTO result = servicioCuenta.setSaldo(1L , accountBalanceDTO);
        assertNotNull(result);
        assertNotEquals(dto.getSaldo() , result.getSaldo());
    }
    @Test
    void testupdateAccount() {
        InputCuentaUpdateDTO inputCuentaDTO = new InputCuentaUpdateDTO(330000.0 , "ct5" , true);
        when(repositorioCuenta.findById(1L)).thenReturn(Optional.of(getMockedCuenta()));
       OutputCuentaDTO result = servicioCuenta.updateAccount(1L , inputCuentaDTO);
       assertNotNull(result);
       assertNotEquals(getMockedCuenta().getSaldo() , result.getSaldo());
       assertNotEquals(getMockedCuenta().getIsDisable() , result.getIsDisable());
       assertNotEquals(getMockedCuenta().getCuentaMercadoPago() , result.getCuentaMP());
    }
    @Test
    void testDeleteAccount() {
        Optional<Cuenta> optionalCuenta =  Optional.of(getMockedCuenta());
        when(repositorioCuenta.findById(1L)).thenReturn(optionalCuenta);
        OutputCuentaDTO result = servicioCuenta.deleteAccount(1L);
        assertNotNull(result);
        assertEquals(optionalCuenta.get().getIdCuenta() , result.getId());
    }
    @Test
    void testgetByOwnerEmail() {
        String email = "email";
        Optional<Cuenta> optionalCuenta =  Optional.of(getMockedCuenta());
        when(repositorioCuenta.findByEmailOwnerAccount(email)).thenReturn(optionalCuenta);
        OutputCuentaDTO result = servicioCuenta.getByOwnerEmail(email);
        assertNotNull(result);
        assertEquals(optionalCuenta.get().getIdCuenta() , result.getId());
        assertEquals(optionalCuenta.get().getEmailOwnerAccount() , result.getOwnerEmail());
    }
    @Test
    void testAvailableEmail() {
        String email = "email";
        when(repositorioCuenta.existsByEmailOwnerAccount(email)).thenReturn(true);
        boolean result = servicioCuenta.avialableEmail(email);
        assertFalse(result);


    }

    private OutputCuentaDTO getMockedCuentaDTO() {
        OutputCuentaDTO outputCuentaDTO = new OutputCuentaDTO();
        outputCuentaDTO.setCuentaMP("ct1");
        outputCuentaDTO.setSaldo(3000.0);
        outputCuentaDTO.setId(1L);
        outputCuentaDTO.setOwnerEmail("email");
        outputCuentaDTO.setPassword("password");
        outputCuentaDTO.setIsDisable(Boolean.FALSE);
        return outputCuentaDTO;

    }
    private Cuenta getMockedCuenta() {
        Cuenta cuenta = new Cuenta();
        cuenta.setCuentaMercadoPago("ct1");
        cuenta.setIdCuenta(1L);
        cuenta.setSaldo(3000.0);
        cuenta.setEmailOwnerAccount("email");
        cuenta.setPassword("password");
        cuenta.setIsDisable(Boolean.FALSE);
        return cuenta;

    }

}
