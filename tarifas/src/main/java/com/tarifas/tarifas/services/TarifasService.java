package com.tarifas.tarifas.services;

import com.tarifas.tarifas.DTOs.TarifaDTO;
import com.tarifas.tarifas.entities.Tarifa;
import com.tarifas.tarifas.repositories.TarifasRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TarifasService {

    private final TarifasRepository tarifasRepository;

    @Autowired
    public TarifasService(
            TarifasRepository tarifasRepository
    ) {
        this.tarifasRepository = tarifasRepository;
    }

    public List<TarifaDTO> getTarifas() {
        return tarifasRepository.findAll().stream().map(TarifaDTO::new).toList();
    }

    public TarifaDTO upsertTarifa(TarifaDTO tarifa) {
        Tarifa t = new Tarifa(tarifa.getId(), tarifa.getTipoTarifa(), tarifa.getMonto(), tarifa.getDescripcion());
        Tarifa tarifaModificada = tarifasRepository.save(t);
        return new TarifaDTO(tarifaModificada);
    }

    public TarifaDTO getTarifaPorTipo(String tipo) {
        Tarifa response = tarifasRepository.findByTipoTarifa(tipo);
        return new TarifaDTO(response);
    }
}
