package com.monopatines.monopatines.entities;

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
    private double latitudParada;
    private double longitudParada;
    private List<Scooter> scooters;

    public Parada() {
    }

    public Parada(String nombreParada, double latitudParada, double longitudParada) {
        this.nombreParada = nombreParada;
        this.scooters = new ArrayList<>();
        this.latitudParada = latitudParada;
        this.longitudParada = longitudParada;

    }

    public boolean addScooter(Scooter scooter) {
        return scooters.add(scooter);
    }

    public boolean removeScooter(Scooter scooter) {
        return scooters.remove(scooter);
    }
}
