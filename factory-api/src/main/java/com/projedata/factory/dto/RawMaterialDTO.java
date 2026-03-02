package com.projedata.factory.dto;

import java.math.BigDecimal;

import com.projedata.factory.entity.RawMaterial;

public record RawMaterialDTO (Long id, String name, BigDecimal quantity) {

	public RawMaterialDTO(RawMaterial rawMaterial) {
		this(rawMaterial.getId(), rawMaterial.getName(), rawMaterial.getQuantity());
	}
}