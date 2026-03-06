package com.projedata.factory.dto;

import java.math.BigDecimal;

import com.projedata.factory.entity.ProductRawMaterial;

public record ProductRawMaterialDTO(Long id, Long productId, Long rawMaterialId, BigDecimal quantity) {

    public ProductRawMaterialDTO(ProductRawMaterial entity) {
        this(
            entity.getId(),
            entity.getProduct().getId(),
            entity.getRawMaterial().getId(),
            entity.getQuantity()
        );
    }
}