package com.projedata.factory.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.projedata.factory.dto.ProductRawMaterialResponseDTO;
import com.projedata.factory.dto.ProductRawMaterialRequestDTO;
import com.projedata.factory.service.ProductRawMaterialService;

@Controller
@RequestMapping("/product-raw-materials")
public class ProductRawMaterialController {

    @Autowired
    private ProductRawMaterialService productRawMaterialService;

    @GetMapping("/product/{productId}")
    public ResponseEntity<List<ProductRawMaterialResponseDTO>> getByProduct(@PathVariable Long productId) {
        return ResponseEntity.ok(productRawMaterialService.getByProduct(productId));
    }

    @PostMapping
    public ResponseEntity<ProductRawMaterialResponseDTO> create(@RequestBody @Validated ProductRawMaterialRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(productRawMaterialService.create(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductRawMaterialResponseDTO> update(
            @PathVariable Long id,
            @RequestBody @Validated ProductRawMaterialRequestDTO dto) {
        return ResponseEntity.ok(productRawMaterialService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        productRawMaterialService.delete(id);
        return ResponseEntity.noContent().build();
    }
}