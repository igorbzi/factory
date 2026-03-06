package com.projedata.factory.dto;

import java.math.BigDecimal;
import java.util.List;

import com.projedata.factory.entity.Product;


public record ProductDTO(Long id, String name, BigDecimal price, List<ProductRawMaterialResponseDTO> rawMaterials) {

    public ProductDTO(Product product) {
        this(
            product.getId(),
            product.getName(),
            product.getPrice(),
            product.getRawMaterials().stream().map(ProductRawMaterialResponseDTO::new).toList()
        );
    }
}
