package com.projedata.factory.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.projedata.factory.dto.RawMaterialDTO;
import com.projedata.factory.entity.RawMaterial;
import com.projedata.factory.repository.RawMaterialRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class RawMaterialService {

    @Autowired
    private RawMaterialRepository rawMaterialRepository;

    public List<RawMaterialDTO> getAll() {
        return rawMaterialRepository.findAll().stream()
                .map(RawMaterialDTO::new)
                .toList();
    }

    public RawMaterialDTO getById(Long id) {
        RawMaterial rawMaterial = findOrThrow(id);
        return new RawMaterialDTO(rawMaterial);
    }

    public RawMaterialDTO create(RawMaterialDTO dto) {
        RawMaterial rawMaterial = new RawMaterial(null, dto.name(), dto.quantity());
        rawMaterialRepository.save(rawMaterial);
        return new RawMaterialDTO(rawMaterial);
    }

    public RawMaterialDTO update(Long id, RawMaterialDTO dto) {
        RawMaterial rawMaterial = findOrThrow(id);
        rawMaterial.setName(dto.name());
        rawMaterial.setQuantity(dto.quantity());
        rawMaterialRepository.save(rawMaterial);
        return new RawMaterialDTO(rawMaterial);
    }

    public void delete(Long id) {
        findOrThrow(id);
        rawMaterialRepository.deleteById(id);
    }

    private RawMaterial findOrThrow(Long id) {
        return rawMaterialRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Raw material not found with id: " + id));
    }
}
