package com.monopatines.monopatines.services;

import com.monopatines.monopatines.DTO.Scooter.ScooterFiltersDTO;
import com.monopatines.monopatines.DTO.Scooter.ScooterInputDTO;
import com.monopatines.monopatines.DTO.Scooter.ScooterOutputDTO;
import com.monopatines.monopatines.DTO.Scooter.ScooterStatusDTO;

import com.monopatines.monopatines.entities.Scooter;

import com.monopatines.monopatines.exceptions.BadRequestException;
import com.monopatines.monopatines.exceptions.ScooterNotFound;
import com.monopatines.monopatines.repositories.ScooterRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.mongodb.core.MongoTemplate;

import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import org.springframework.stereotype.Service;
import org.springframework.data.domain.Sort;

import java.util.List;

@Service
public class ScooterService {

    private final ScooterRepository scooterRepository;
    private final MongoTemplate mongoTemplate;

        @Autowired
        public ScooterService(ScooterRepository scooterRepository,MongoTemplate mongoTemplate ) {
                this.scooterRepository = scooterRepository;
                this.mongoTemplate = mongoTemplate;
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
        public List<ScooterOutputDTO> getAllScooters(ScooterFiltersDTO scooterFiltersDTO) {
            Query query = new Query();

            // Filtros dinámicos
            if (scooterFiltersDTO.getLocation() != null && !scooterFiltersDTO.getLocation().isEmpty()) {
                query.addCriteria(Criteria.where("location").is(scooterFiltersDTO.getLocation()));
            }
            if (scooterFiltersDTO.getStatus() != null && !scooterFiltersDTO.getStatus().isEmpty()) {
                query.addCriteria(Criteria.where("status").is(scooterFiltersDTO.getStatus()));
            }
            if (scooterFiltersDTO.getModel() != null && !scooterFiltersDTO.getModel().isEmpty()) {
                query.addCriteria(Criteria.where("model").is(scooterFiltersDTO.getModel()));
            }

            // Ejecutar la consulta
            List<Scooter> scooters = mongoTemplate.find(query, Scooter.class);
            return scooters.stream().map(ScooterOutputDTO::new).toList();
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
