package com.projedata.factory.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.projedata.factory.dto.ProductDTO;
import com.projedata.factory.entity.Product;
import com.projedata.factory.repository.ProductRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public List<ProductDTO> getAll() {
        return productRepository.findAll().stream()
                .map(ProductDTO::new)
                .toList();
    }

    public ProductDTO getById(Long id) {
        Product product = findOrThrow(id);
        return new ProductDTO(product);
    }

    public ProductDTO create(ProductDTO dto) {
        Product product = new Product(null, dto.name(), dto.price(), null);
        productRepository.save(product);
        return new ProductDTO(product);
    }

    public ProductDTO update(Long id, ProductDTO dto) {
        Product product = findOrThrow(id);
        product.setName(dto.name());
        product.setPrice(dto.price());
        productRepository.save(product);
        return new ProductDTO(product);
    }

    public void delete(Long id) {
        findOrThrow(id);
        productRepository.deleteById(id);
    }

    private Product findOrThrow(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Product not found with id: " + id));
    }
}
