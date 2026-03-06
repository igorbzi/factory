package com.projedata.factory.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.projedata.factory.dto.ProductRawMaterialResponseDTO;
import com.projedata.factory.dto.ProductRawMaterialRequestDTO;
import com.projedata.factory.entity.Product;
import com.projedata.factory.entity.ProductRawMaterial;
import com.projedata.factory.entity.RawMaterial;
import com.projedata.factory.exception.ResourceNotFoundException;
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

    public List<ProductRawMaterialResponseDTO> getByProduct(Long productId) {
        return productRawMaterialRepository.findByProductId(productId).stream()
                .map(ProductRawMaterialResponseDTO::new)
                .toList();
    }

    public ProductRawMaterialResponseDTO create(ProductRawMaterialRequestDTO dto) {
        Product product = productRepository.findById(dto.productId())
                .orElseThrow(() -> new ResourceNotFoundException("Product", dto.productId()));

        RawMaterial rawMaterial = rawMaterialRepository.findById(dto.rawMaterialId())
                .orElseThrow(() -> new ResourceNotFoundException("Raw material", dto.rawMaterialId()));

        ProductRawMaterial entity = new ProductRawMaterial(product, rawMaterial, dto.quantity());
        productRawMaterialRepository.save(entity);
        return new ProductRawMaterialResponseDTO(entity);
    }

    public ProductRawMaterialResponseDTO update(Long id, ProductRawMaterialRequestDTO dto) {
        ProductRawMaterial entity = findOrThrow(id);
        entity.setQuantity(dto.quantity());
        productRawMaterialRepository.save(entity);
        return new ProductRawMaterialResponseDTO(entity);
    }

    public void delete(Long id) {
        findOrThrow(id);
        productRawMaterialRepository.deleteById(id);
    }

    private ProductRawMaterial findOrThrow(Long id) {
        return productRawMaterialRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product ingredient", id));
    }
}