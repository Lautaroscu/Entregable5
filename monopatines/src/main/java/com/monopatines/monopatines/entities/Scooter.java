package com.monopatines.monopatines.entities;


import com.monopatines.monopatines.Enumns.ScooterStatus;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "scooters") // Especifica el nombre de la colección en MongoDB
@Getter
@Setter
public class Scooter {
    @Id
    private String id; // El campo que actuará como el identificador único en MongoDB
    private String model;
    private ScooterStatus status; // Estado del monopatín: "Available", "In Use", "Paused", etc.
    private double latitude;
    private double longitude;

    public Scooter() {
    }

    public Scooter(double latitude, double longitude, String model) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.model = model;
        this.status = ScooterStatus.AVAILABLE;
    }
}
