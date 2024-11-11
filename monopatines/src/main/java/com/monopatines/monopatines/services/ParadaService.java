package com.monopatines.monopatines.services;

import com.monopatines.monopatines.DTO.Parada.ParadaInputDTO;
import com.monopatines.monopatines.DTO.Parada.ParadaOutputDTO;
import com.monopatines.monopatines.entities.Parada;
import com.monopatines.monopatines.entities.Scooter;
import com.monopatines.monopatines.exceptions.BadRequestException;
import com.monopatines.monopatines.exceptions.ParadaNotFound;
import com.monopatines.monopatines.repositories.ParadaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ParadaService {
    private final ParadaRepository paradaRepository;

    @Autowired
    public ParadaService(ParadaRepository paradaRepository) {
        this.paradaRepository = paradaRepository;
    }

    public ParadaOutputDTO createParada(ParadaInputDTO paradaInputDTO) {
        try {
            Parada parada = new Parada(paradaInputDTO.getNombreParada());
            parada = paradaRepository.save(parada);
            return new ParadaOutputDTO(parada);
        }catch (BadRequestException badRequestException){
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

        // Si se incluye scooters en la solicitud, actualizamos la lista de scooters
        if (paradaInputDTO.getScooters() != null) {
            // Asignación completa de scooters (sobrescribe la lista actual)
            paradaToUpdate.setScooters(paradaInputDTO.getScooters());
        }

        Parada updatedParada = paradaRepository.save(paradaToUpdate);
        return new ParadaOutputDTO(updatedParada);
    }

    private boolean isBadRequest(ParadaInputDTO paradaInputDTO) {
        return paradaInputDTO.getNombreParada() == null || paradaInputDTO.getNombreParada().isEmpty();
    }
}
