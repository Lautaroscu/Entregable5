package com.monopatines.monopatines.DTO.Parada;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ParadaInputDTO implements Serializable {
    private String nombreParada;
    private double latitudParada;
    private double longitudParada;


}
