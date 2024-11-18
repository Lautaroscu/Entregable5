package com.reportes.reportes.repositories;

import com.reportes.reportes.entities.Tarifa;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
@Testcontainers
public class ReporteRepositoryTest {

    //Crea un container de prueba en docker para realizar las pruebas
    @Container
    static PostgreSQLContainer<?> postgresContainer = new PostgreSQLContainer<>("postgres:latest")
            .withDatabaseName("testdb")
            .withUsername("testuser")
            .withPassword("testpass");

    @Autowired
    private ReporteRepository reporteRepository;

    //Este metodo configura el container creado para finalidad de pruebas
    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgresContainer::getJdbcUrl);
        registry.add("spring.datasource.username", postgresContainer::getUsername);
        registry.add("spring.datasource.password", postgresContainer::getPassword);
    }

    @Test
    void testFindByTipoTarifa() {
        Tarifa tarifa = new Tarifa();
        tarifa.setTipoTarifa("TipoTest");
        tarifa.setMonto(new BigDecimal("100.0"));
        tarifa.setDescripcion("Descripcion Test");
        reporteRepository.save(tarifa);

        Tarifa foundTarifa = reporteRepository.findByTipoTarifa("TipoTest");

        assertNotNull(foundTarifa);
        assertEquals("TipoTest", foundTarifa.getTipoTarifa());
        assertEquals(new BigDecimal("100.0"), foundTarifa.getMonto());
        assertEquals("Descripcion Test", foundTarifa.getDescripcion());
    }
}
