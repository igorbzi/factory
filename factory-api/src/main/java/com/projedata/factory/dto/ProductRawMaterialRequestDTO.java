package com.projedata.factory.dto;

import java.math.BigDecimal;

import com.projedata.factory.entity.ProductRawMaterial;

public record ProductRawMaterialRequestDTO(Long rawMaterialId, Long productId, BigDecimal quantity) {
	
    public ProductRawMaterialRequestDTO(ProductRawMaterial prm) {
        this(
            prm.getRawMaterial().getId(),
            prm.getProduct().getId(),
            prm.getQuantity()
        );
    }
}