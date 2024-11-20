package com.paradas.paradas.DTOs;

import com.paradas.paradas.entities.Parada;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class ParadaOutputDTO implements Serializable {
    private String idParada;
    private String nombreParada;
    private double latitudParada;
    private double longitudParada;
    private List<String> scootersId;

    public ParadaOutputDTO(Parada parada) {
        this.idParada = parada.getIdParada();
        this.nombreParada = parada.getNombreParada();
        this.latitudParada = parada.getLatitudParada();
        this.longitudParada = parada.getLongitudParada();
        // Verificar si la lista de scooters es null y manejarlo
        this.scootersId = parada.getScootersId() != null
                ? new ArrayList<>(parada.getScootersId())
                : new ArrayList<>();  // Inicializar como lista vacía si es null
    }
}
