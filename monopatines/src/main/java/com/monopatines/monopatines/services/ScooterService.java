package com.monopatines.monopatines.services;

import com.monopatines.monopatines.DTO.ScooterInputDTO;
import com.monopatines.monopatines.DTO.ScooterOutputDTO;
import com.monopatines.monopatines.DTO.ScooterStatusDTO;
import com.monopatines.monopatines.entities.Scooter;
import com.monopatines.monopatines.exceptions.BadRequestException;
import com.monopatines.monopatines.exceptions.ScooterNotFound;
import com.monopatines.monopatines.repositories.ScooterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScooterService {
private final ScooterRepository scooterRepository;

    @Autowired
    public ScooterService(ScooterRepository scooterRepository) {
            this.scooterRepository = scooterRepository;
    }

    public ScooterOutputDTO createScooter(ScooterInputDTO scooterInputDTO) {
        try {
            Scooter scooter = new Scooter(scooterInputDTO.getLocation() , scooterInputDTO.getModel());
            scooter = scooterRepository.save(scooter);
            return new ScooterOutputDTO(scooter);
        }catch (BadRequestException badRequestException){
            throw new BadRequestException(badRequestException.getMessage());
        }


    }
    public List<ScooterOutputDTO> getAllScooters() {
        return scooterRepository.findAll()
                .stream()
                .map(ScooterOutputDTO::new)
                .toList();
    }
    public ScooterOutputDTO getScooterById(String id) {
        Scooter scooter = scooterRepository.findById(id).orElseThrow(() -> new ScooterNotFound("Scooter not found"));
        return new ScooterOutputDTO(scooter);
    }
    public ScooterOutputDTO updateScooter(ScooterInputDTO scooterInputDTO , String id) {
        if(isBadRequest(scooterInputDTO)) {
            throw new BadRequestException("Scooter invalido");
        }
        Scooter scooterToUpdate = scooterRepository.findById(id).orElseThrow(() -> new ScooterNotFound("id"));
        scooterToUpdate.setLocation(scooterInputDTO.getLocation());
        scooterToUpdate.setModel(scooterInputDTO.getModel());
        scooterRepository.save(scooterToUpdate);
        return new ScooterOutputDTO(scooterToUpdate);

    }
    private boolean isBadRequest(ScooterInputDTO scooterInputDTO) {
        return scooterInputDTO.getLocation() == null || scooterInputDTO.getLocation().isEmpty() || scooterInputDTO.getModel() == null
                || scooterInputDTO.getModel().isEmpty();

    }

    public void deleteScooter(String id) {
        if(!scooterRepository.existsById(id)) {
            throw new ScooterNotFound("Scooter not found");
        }
        scooterRepository.deleteById(id);
    }
    public void setStatus(ScooterStatusDTO scooterStatus, String id) {
        Scooter scooter = scooterRepository.findById(id).orElseThrow(() -> new ScooterNotFound("id"));
        scooter.setStatus(scooterStatus.getScooterStatus());
        scooterRepository.save(scooter);
    }



}
