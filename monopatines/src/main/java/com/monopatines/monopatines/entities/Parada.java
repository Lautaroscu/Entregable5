package com.monopatines.monopatines.entities;


import com.monopatines.monopatines.DTO.Scooter.ScooterOutputDTO;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "paradas")
@Getter
@Setter
public class Parada {

    @Id
    private String idParada;
    private String nombreParada;
    private List<ScooterOutputDTO> scooters;

    public Parada(){}
    public  Parada(String nombreParada){
        this.nombreParada = nombreParada;
        this.scooters = new ArrayList<>();
    }

}
