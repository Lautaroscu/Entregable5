//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package controllers;

import com.cuentas.cuentas.DTO.AccountAvailabilityDTO;
import com.cuentas.cuentas.DTO.AccountBalanceDTO;
import com.cuentas.cuentas.DTO.InputCuentaDTO;
import com.cuentas.cuentas.DTO.InputCuentaUpdateDTO;
import com.cuentas.cuentas.DTO.OutputCuentaDTO;
import com.cuentas.cuentas.controladores.CuentaControlador;
import com.cuentas.cuentas.servicios.ServicioCuenta;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class CuentaControllerTest {
    @Mock
    private ServicioCuenta servicioCuenta;
    @InjectMocks
    private CuentaControlador cuentaControlador;

    public CuentaControllerTest() {
    }

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testObtenerCuentas() {
        OutputCuentaDTO outputCuentaDTO = new OutputCuentaDTO();
        outputCuentaDTO.setCuentaMP("ctaMP1");
        outputCuentaDTO.setSaldo((double)2000.0F);
        outputCuentaDTO.setId(1L);
        outputCuentaDTO.setFechaAlta(LocalDate.now());
        outputCuentaDTO.setOwnerEmail("ownerEmail");
        outputCuentaDTO.setPassword("password");
        ArrayList<OutputCuentaDTO> mockedList = new ArrayList();
        mockedList.add(outputCuentaDTO);
        Mockito.when(servicioCuenta.getCuentas()).thenReturn(mockedList);
        ResponseEntity<List<OutputCuentaDTO>> response = cuentaControlador.getCuentas();
        assertEquals(HttpStatus.OK, response.getStatusCode());
       verify(servicioCuenta, times(1)).getCuentas();
    }

    @Test
    public void testObtenerCuenta() {
        OutputCuentaDTO outputCuentaDTO = new OutputCuentaDTO();
        outputCuentaDTO.setCuentaMP("ctaMP1");
        outputCuentaDTO.setSaldo((double)2000.0F);
        outputCuentaDTO.setId(1L);
        outputCuentaDTO.setFechaAlta(LocalDate.now());
        outputCuentaDTO.setOwnerEmail("ownerEmail");
        outputCuentaDTO.setPassword("password");
        Long id = outputCuentaDTO.getId();
        Mockito.when(servicioCuenta.getAccountById(id)).thenReturn(outputCuentaDTO);
        ResponseEntity<OutputCuentaDTO> response = cuentaControlador.getById(id);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(servicioCuenta, times(1)).getAccountById(id);
    }

    @Test
    public void testCrearCuenta() {
        InputCuentaDTO inputCuentaDTO = new InputCuentaDTO("ct1", "n1", "a1", "c1", "e1@mail", 2000.0, false, "pass1");
        OutputCuentaDTO outputCuentaDTO = new OutputCuentaDTO();
        outputCuentaDTO.setCuentaMP("ctaMP1");
        outputCuentaDTO.setSaldo(2000.0);
        outputCuentaDTO.setId(1L);
        outputCuentaDTO.setFechaAlta(LocalDate.now());
        outputCuentaDTO.setOwnerEmail("ownerEmail");
        outputCuentaDTO.setPassword("password");
        Mockito.when(servicioCuenta.crearCuenta(inputCuentaDTO)).thenReturn(outputCuentaDTO);
        ResponseEntity<OutputCuentaDTO> response = cuentaControlador.createAccount(inputCuentaDTO);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
       verify(servicioCuenta, times(1)).crearCuenta(inputCuentaDTO);
    }

    @Test
    public void testActualizarCuenta() {
        InputCuentaUpdateDTO inputCuentaUpdateDTO = new InputCuentaUpdateDTO();
        inputCuentaUpdateDTO.setCuentaMP("ctaMP2");
        inputCuentaUpdateDTO.setSaldo(0.0);
        inputCuentaUpdateDTO.setIsDisable(true);
        OutputCuentaDTO outputCuentaDTO = new OutputCuentaDTO();
        outputCuentaDTO.setCuentaMP(inputCuentaUpdateDTO.getCuentaMP());
        outputCuentaDTO.setSaldo(inputCuentaUpdateDTO.getSaldo());
        outputCuentaDTO.setSaldo((double)2000.0F);
        outputCuentaDTO.setIsDisable(inputCuentaUpdateDTO.getIsDisable());
        Mockito.when(servicioCuenta.updateAccount(1L, inputCuentaUpdateDTO)).thenReturn(outputCuentaDTO);
        ResponseEntity<OutputCuentaDTO> response = cuentaControlador.updateAccount(1L, inputCuentaUpdateDTO);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
         verify(servicioCuenta, times(1)).updateAccount(1L, inputCuentaUpdateDTO);
    }

    @Test
    public void testObtenerCuentaByOwnerEmail() {
        String ownerEmail = "ownerEmail";
        OutputCuentaDTO outputCuentaDTO = new OutputCuentaDTO();
        outputCuentaDTO.setCuentaMP("ctaMP1");
        outputCuentaDTO.setSaldo(2000.0);
        outputCuentaDTO.setId(1L);
        outputCuentaDTO.setFechaAlta(LocalDate.now());
        outputCuentaDTO.setOwnerEmail(ownerEmail);
        outputCuentaDTO.setPassword("password");
        Mockito.when(servicioCuenta.getByOwnerEmail(ownerEmail)).thenReturn(outputCuentaDTO);
        ResponseEntity<OutputCuentaDTO> response =cuentaControlador.getByOwnerEmail(ownerEmail);
        assertEquals(HttpStatus.OK, response.getStatusCode());
       verify(servicioCuenta, times(1)).getByOwnerEmail(ownerEmail);
    }

    @Test
    public void testEmailAvailable() {
        String ownerEmail = "ownerEmail";
        Mockito.when(servicioCuenta.avialableEmail(ownerEmail)).thenReturn(true);
        ResponseEntity<Boolean> response =cuentaControlador.checkEmail(ownerEmail);
        assertEquals(Boolean.TRUE, response.getBody());
     verify(servicioCuenta, times(1)).avialableEmail(ownerEmail);
    }

    @Test
    public void testManageAvailability() {
        AccountAvailabilityDTO accountAvailabilityDTO = new AccountAvailabilityDTO(1L, false);
        OutputCuentaDTO outputCuentaDTO = new OutputCuentaDTO();
        outputCuentaDTO.setCuentaMP("ctaMP1");
        outputCuentaDTO.setSaldo(2000.0);
        outputCuentaDTO.setId(1L);
        outputCuentaDTO.setFechaAlta(LocalDate.now());
        outputCuentaDTO.setOwnerEmail("ownerEmail");
        outputCuentaDTO.setPassword("password");
        outputCuentaDTO.setIsDisable(accountAvailabilityDTO.isAvailable());
        Mockito.when(servicioCuenta.manageAvailability(accountAvailabilityDTO)).thenReturn(outputCuentaDTO);
        ResponseEntity<OutputCuentaDTO> response = cuentaControlador.manageAccount(accountAvailabilityDTO);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        ((ServicioCuenta) verify(servicioCuenta, times(1))).manageAvailability(accountAvailabilityDTO);
    }

    @Test
    public void testUpdateBalance() {
        AccountBalanceDTO accountBalanceDTO = new AccountBalanceDTO((double)4000.0F);
        OutputCuentaDTO outputCuentaDTO = new OutputCuentaDTO();
        outputCuentaDTO.setCuentaMP("ctaMP1");
        outputCuentaDTO.setSaldo(accountBalanceDTO.getSaldo());
        outputCuentaDTO.setId(1L);
        outputCuentaDTO.setFechaAlta(LocalDate.now());
        outputCuentaDTO.setOwnerEmail("ownerEmail");
        outputCuentaDTO.setPassword("password");
        outputCuentaDTO.setIsDisable(true);
        Mockito.when(servicioCuenta.setSaldo(outputCuentaDTO.getId(), accountBalanceDTO)).thenReturn(outputCuentaDTO);
        ResponseEntity<OutputCuentaDTO> response = this.cuentaControlador.updateSaldo(outputCuentaDTO.getId(), accountBalanceDTO);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        verify(this.servicioCuenta, times(1)).setSaldo(outputCuentaDTO.getId(), accountBalanceDTO);
    }

    @Test
    public void testDeleteAccount() {
        OutputCuentaDTO outputCuentaDTO = new OutputCuentaDTO();
        outputCuentaDTO.setCuentaMP("ctaMP1");
        outputCuentaDTO.setSaldo((double)2000.0F);
        outputCuentaDTO.setId(1L);
        outputCuentaDTO.setFechaAlta(LocalDate.now());
        outputCuentaDTO.setOwnerEmail("ownerEmail");
        outputCuentaDTO.setPassword("password");
        outputCuentaDTO.setIsDisable(true);
        Mockito.when(this.servicioCuenta.deleteAccount(outputCuentaDTO.getId())).thenReturn(outputCuentaDTO);
        ResponseEntity<OutputCuentaDTO> response = this.cuentaControlador.deleteAccount(outputCuentaDTO.getId());
        assertEquals(HttpStatus.OK, response.getStatusCode());
         verify(this.servicioCuenta, times(1)).deleteAccount(outputCuentaDTO.getId());
    }
}
