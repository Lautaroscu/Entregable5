package com.tarifas.tarifas.repositories;

import com.tarifas.tarifas.entities.Tarifa;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class TarifasRepositoryIntegrationTest {

    @Autowired
    TarifasRepository repository;

    @Test
    public void findTarifaByTipo_AlEncontrarResultado_DevuelveRespuestaSatisfactoria() {
        String tipoTarifa = "TarifaTest";
        Tarifa t = new Tarifa();
        t.setTipoTarifa(tipoTarifa);
        t.setMonto(new BigDecimal("200.0"));
        t.setDescripcion("Tarifa de prueba");

        repository.save(t);

        Tarifa tarifaEncontrada = repository.findByTipoTarifa(tipoTarifa);

        assertNotNull(tarifaEncontrada);
        assertEquals(tarifaEncontrada.getTipoTarifa(), tipoTarifa);
        assertEquals(tarifaEncontrada.getDescripcion(), t.getDescripcion());

        repository.deleteById(tarifaEncontrada.getId());
    }
}
