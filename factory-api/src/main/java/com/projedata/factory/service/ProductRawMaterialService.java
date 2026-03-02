package com.projedata.factory.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.projedata.factory.dto.ProductRawMaterialDTO;
import com.projedata.factory.entity.Product;
import com.projedata.factory.entity.ProductRawMaterial;
import com.projedata.factory.entity.RawMaterial;
import com.projedata.factory.repository.ProductRawMaterialRepository;
import com.projedata.factory.repository.ProductRepository;
import com.projedata.factory.repository.RawMaterialRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class ProductRawMaterialService {

    @Autowired
    private ProductRawMaterialRepository productRawMaterialRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private RawMaterialRepository rawMaterialRepository;

    public List<ProductRawMaterialDTO> getByProduct(Long productId) {
        return productRawMaterialRepository.findByProductId(productId).stream()
                .map(ProductRawMaterialDTO::new)
                .toList();
    }

    public ProductRawMaterialDTO create(ProductRawMaterialDTO dto) {
        Product product = productRepository.findById(dto.productId())
                .orElseThrow(() -> new EntityNotFoundException("Product not found with id: " + dto.productId()));

        RawMaterial rawMaterial = rawMaterialRepository.findById(dto.rawMaterialId())
                .orElseThrow(() -> new EntityNotFoundException("Raw material not found with id: " + dto.rawMaterialId()));

        ProductRawMaterial entity = new ProductRawMaterial(product, rawMaterial, dto.quantity());
        productRawMaterialRepository.save(entity);
        return new ProductRawMaterialDTO(entity);
    }

    /*Only updates quantity*/
    public ProductRawMaterialDTO update(Long id, ProductRawMaterialDTO dto) {
        ProductRawMaterial entity = findOrThrow(id);
        entity.setQuantity(dto.quantity());
        productRawMaterialRepository.save(entity);
        return new ProductRawMaterialDTO(entity);
    }

    public void delete(Long id) {
        findOrThrow(id);
        productRawMaterialRepository.deleteById(id);
    }

    private ProductRawMaterial findOrThrow(Long id) {
        return productRawMaterialRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Relation not found with id: " + id));
    }
}