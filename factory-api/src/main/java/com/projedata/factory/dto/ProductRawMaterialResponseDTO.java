package com.projedata.factory.dto;

import java.math.BigDecimal;

import com.projedata.factory.entity.ProductRawMaterial;

public record ProductRawMaterialResponseDTO(Long id, RawMaterialDTO rawMaterial, BigDecimal quantity) {

    public ProductRawMaterialResponseDTO(ProductRawMaterial entity) {
        this(
            entity.getId(),
            new RawMaterialDTO(
                entity.getRawMaterial().getId(),
                entity.getRawMaterial().getName(),
                entity.getRawMaterial().getQuantity()
            ),
            entity.getQuantity()
        );
    }
}