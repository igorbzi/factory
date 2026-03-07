package com.projedata.factory.dto;

import java.math.BigDecimal;

import com.projedata.factory.entity.ProductRawMaterial;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record ProductRawMaterialRequestDTO(
		@NotNull Long rawMaterialId, 
		@NotNull Long productId, 
		@NotNull @Positive BigDecimal quantity
		) {
	
    public ProductRawMaterialRequestDTO(ProductRawMaterial prm) {
        this(
            prm.getRawMaterial().getId(),
            prm.getProduct().getId(),
            prm.getQuantity()
        );
    }
}