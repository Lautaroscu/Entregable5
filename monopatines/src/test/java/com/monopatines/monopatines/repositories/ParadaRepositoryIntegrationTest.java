package com.monopatines.monopatines.repositories;

import com.monopatines.monopatines.entities.Parada;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class ParadaRepositoryIntegrationTest {

    @Autowired
    private ParadaRepository paradaRepository;

    @Test
    public void findByLocation_AlEncontrarParada_DevuelveResultadoSatisfactorio() {
        // Arrange: Crea una parada de prueba
        String tipoParada = "ParadaTest";
        Parada parada = new Parada();
        parada.setIdParada("1");
        parada.setNombreParada(tipoParada);
        parada.setLatitudParada(12.3456);
        parada.setLongitudParada(65.4321);

        // Guarda la parada
        paradaRepository.save(parada);

        // Act: Busca la parada por ubicación
        var result = paradaRepository.findByLocation(12.3456, 65.4321);

        // Assert: Verifica que la parada se haya encontrado correctamente
        assertNotNull(result);
        assertEquals(1, result.size()); // Asegúrate de que la búsqueda devuelva un solo resultado
        assertEquals(parada.getIdParada(), result.get(0).getIdParada());
        assertEquals(parada.getNombreParada(), result.get(0).getNombreParada());

        // Limpieza: Elimina la parada creada
        paradaRepository.deleteById(parada.getIdParada());
    }}
