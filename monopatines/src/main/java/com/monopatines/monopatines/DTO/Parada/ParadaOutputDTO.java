package com.monopatines.monopatines.DTO.Parada;

import com.monopatines.monopatines.DTO.Scooter.ScooterOutputDTO;
import com.monopatines.monopatines.entities.Parada;
import lombok.Getter;
import lombok.Setter;
import java.io.Serializable;
import java.util.List;

@Getter
@Setter
public class ParadaOutputDTO implements Serializable {
    private String idParada;
    private String nombreParada;
    private List<String> scooters;

    public ParadaOutputDTO(Parada parada) {
        this.idParada = parada.getIdParada();
        this.nombreParada = parada.getNombreParada();
        this.scooters = parada.getScooters();
    }
}