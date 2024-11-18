package com.reportes.reportes.repositories;

import com.reportes.reportes.entities.Tarifa;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@SpringBootTest
public class ReporteRepositoryIntegrationTest {

    @Autowired
    ReporteRepository repository;

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


