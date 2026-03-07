package com.projedata.factory.dto;

import java.math.BigDecimal;

import com.projedata.factory.entity.RawMaterial;
import com.projedata.factory.enumerators.MeasurementUnit;

import jakarta.validation.constraints.NotNull;

public record RawMaterialDTO(
		Long id, 
		@NotNull String name, 
		@NotNull BigDecimal quantity, 
		@NotNull MeasurementUnit unitOfMeasure
		) {

    public RawMaterialDTO(RawMaterial rawMaterial) {
        this(
            rawMaterial.getId(),
            rawMaterial.getName(),
            rawMaterial.getQuantity(),
            rawMaterial.getMeasurementUnit()
        );
    }
}