package com.monopatines.monopatines.services;

import com.monopatines.monopatines.DTO.Parada.ParadaInputDTO;
import com.monopatines.monopatines.DTO.Parada.ParadaOutputDTO;
import com.monopatines.monopatines.DTO.Scooter.ScooterOutputDTO;
import com.monopatines.monopatines.Enumns.ScooterStatus;
import com.monopatines.monopatines.entities.Parada;
import com.monopatines.monopatines.entities.Scooter;
import com.monopatines.monopatines.exceptions.BadRequestException;
import com.monopatines.monopatines.exceptions.ParadaNotFound;
import com.monopatines.monopatines.exceptions.ScooterNotFound;
import com.monopatines.monopatines.repositories.ParadaRepository;
import com.monopatines.monopatines.repositories.ScooterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ParadaService {
    private final ParadaRepository paradaRepository;
    private final ScooterRepository scooterRepository;

    @Autowired
    public ParadaService(ParadaRepository paradaRepository, ScooterRepository scooterRepository) {
        this.scooterRepository = scooterRepository;
        this.paradaRepository = paradaRepository;
    }

    public ParadaOutputDTO createParada(ParadaInputDTO paradaInputDTO) {
        try {
            Parada parada = new Parada(paradaInputDTO.getNombreParada() , paradaInputDTO.getLatitudParada() , paradaInputDTO.getLongitudParada());
            parada = paradaRepository.save(parada);
            return new ParadaOutputDTO(parada);
        } catch (BadRequestException badRequestException) {
            throw new BadRequestException(badRequestException.getMessage());
        }
    }

    public List<ParadaOutputDTO> getAllParadas() {
        return paradaRepository.findAll()
                .stream()
                .map(ParadaOutputDTO::new)
                .toList();
    }

    public ParadaOutputDTO getParadaById(String id) {
        Parada parada = paradaRepository.findById(id)
                .orElseThrow(() -> new ParadaNotFound("Parada not found"));
        return new ParadaOutputDTO(parada);
    }

    public void deleteParada(String id) {
        if (!paradaRepository.existsById(id)) {
            throw new ParadaNotFound("Parada not found");
        }
        paradaRepository.deleteById(id);
    }

    public ParadaOutputDTO updateParada(ParadaInputDTO paradaInputDTO, String idParada) {
        if (isBadRequest(paradaInputDTO)) {
            throw new BadRequestException("Parada inválida");
        }

        Parada paradaToUpdate = paradaRepository.findById(idParada)
                .orElseThrow(() -> new ParadaNotFound("Parada not found"));

        // Si se incluye nombreParada en la solicitud, actualizamos el nombre
        if (paradaInputDTO.getNombreParada() != null) {
            paradaToUpdate.setNombreParada(paradaInputDTO.getNombreParada());
        }

        Parada updatedParada = paradaRepository.save(paradaToUpdate);
        return new ParadaOutputDTO(updatedParada);
    }

    public void addScooterToParada(String idParada, String idScooter) {
        Parada parada = paradaRepository.findById(idParada)
                .orElseThrow(() -> new ParadaNotFound("Parada not found"));
        Scooter scooter = scooterRepository.findById(idScooter).orElseThrow(() -> new ScooterNotFound("Scooter not found"));
        parada.addScooter(scooter);
        paradaRepository.save(parada);

    }

    public void removeScooterFromParada(String idParada, String idScooter) {
        Parada parada = paradaRepository.findById(idParada)
                .orElseThrow(() -> new ParadaNotFound("Parada not found"));
        Scooter scooter = scooterRepository.findById(idScooter)
                .orElseThrow(() -> new ScooterNotFound("Scooter not found"));

        parada.removeScooter(scooter);
        paradaRepository.save(parada);

    }

    private boolean isBadRequest(ParadaInputDTO paradaInputDTO) {
        return paradaInputDTO.getNombreParada() == null || paradaInputDTO.getNombreParada().isEmpty();
    }
    public List<ScooterOutputDTO> getScootersNearUserLocation(double userLatitude, double userLongitude) {
            List<Scooter> scooters = new ArrayList<>();
            List<Parada> findScootersStopNearUser = paradaRepository.findByLocation(userLatitude , userLongitude);
            for(Parada parada : findScootersStopNearUser) {
                for(Scooter scooter : parada.getScooters()) {
                    if(scooter.getStatus().equals(ScooterStatus.AVAILABLE)) {
                        scooters.add(scooter);
                    }
                }
            }
            return scooters
                    .stream()
                    .map(ScooterOutputDTO::new)
                    .toList();

    }
}
