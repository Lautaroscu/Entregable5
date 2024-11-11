package com.monopatines.monopatines.entities;


import com.monopatines.monopatines.Enumns.ScooterStatus;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.annotation.Id;


@Document(collection = "scooters") // Especifica el nombre de la colección en MongoDB
@Getter
@Setter
public class Scooter {
    @Id
    private String id; // El campo que actuará como el identificador único en MongoDB
    private String model;
    private ScooterStatus status; // Estado del monopatín: "Available", "In Use", "Paused", etc.
    private String location;
    private double kilometersTraveled;
    private double totalTimeUsed; // Tiempo total de uso en minutos


    public Scooter() {}
    public Scooter(String location , String model) {
        this.location = location;
        this.model = model;
        this.status = ScooterStatus.AVAILABLE;
        this.kilometersTraveled = 0;
        this.totalTimeUsed = 0;
    }
}
