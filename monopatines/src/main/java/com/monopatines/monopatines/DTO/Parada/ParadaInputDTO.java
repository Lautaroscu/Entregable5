package com.monopatines.monopatines.DTO.Parada;


import com.monopatines.monopatines.DTO.Scooter.ScooterOutputDTO;

import java.io.Serializable;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ParadaInputDTO implements Serializable {
    private String nombreParada;
    private double latitudParada;
    private double longitudParada;


}
