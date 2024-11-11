package com.monopatines.monopatines.DTO.Parada;


import com.monopatines.monopatines.DTO.Scooter.ScooterOutputDTO;
import java.io.Serializable;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ParadaInputDTO implements Serializable {
    private String nombreParada;
    private List<ScooterOutputDTO> scooters;  // Opcional para recibir scooters al crear la parada, o podría omitirse.
}
