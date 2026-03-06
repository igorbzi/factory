package com.projedata.factory.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.projedata.factory.dto.ProductDTO;
import com.projedata.factory.dto.ProductRequestDTO;
import com.projedata.factory.entity.Product;
import com.projedata.factory.entity.ProductRawMaterial;
import com.projedata.factory.entity.RawMaterial;
import com.projedata.factory.exception.ResourceNotFoundException;
import com.projedata.factory.repository.ProductRepository;
import com.projedata.factory.repository.RawMaterialRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;
    
    @Autowired
    private RawMaterialRepository rawMaterialRepository;

    public List<ProductDTO> getAll() {
        return productRepository.findAllWithIngredients().stream()
                .map(ProductDTO::new)
                .toList();
    }

    public ProductDTO getById(Long id) {
        Product product = productRepository.findByIdWithIngredients(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product", id));
        return new ProductDTO(product);
    }
    
    public ProductDTO create(ProductRequestDTO dto) {
        Product product = new Product(null, dto.name(), dto.price(), new ArrayList<>());

        List<ProductRawMaterial> ingredients = dto.rawMaterials().stream()
                .map(i -> {
                    RawMaterial rawMaterial = rawMaterialRepository.findById(i.rawMaterialId())
                            .orElseThrow(() -> new EntityNotFoundException("Raw material not found with id: " + i.rawMaterialId()));
                    return new ProductRawMaterial(product, rawMaterial, i.quantity());
                })
                .toList();

        product.getRawMaterials().addAll(ingredients);
        productRepository.save(product);
        return new ProductDTO(product);
    }

    public ProductDTO update(Long id, ProductRequestDTO dto) {
        Product product = findOrThrow(id);
        product.setName(dto.name());
        product.setPrice(dto.price());
        product.getRawMaterials().clear();

        List<ProductRawMaterial> ingredients = dto.rawMaterials().stream()
                .map(i -> {
                    RawMaterial rawMaterial = rawMaterialRepository.findById(i.rawMaterialId())
                            .orElseThrow(() -> new EntityNotFoundException("Raw material not found with id: " + i.rawMaterialId()));
                    return new ProductRawMaterial(product, rawMaterial, i.quantity());
                })
                .toList();

        product.getRawMaterials().addAll(ingredients);
        productRepository.save(product);
        return new ProductDTO(product);
    }
    
    public void delete(Long id) {
        findOrThrow(id);
        productRepository.deleteById(id);
    }

	private Product findOrThrow(Long id) {
	    return productRepository.findByIdWithIngredients(id)
	            .orElseThrow(() -> new ResourceNotFoundException("Product", id));
	}
}
