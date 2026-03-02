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

import com.projedata.factory.dto.ProductRawMaterialDTO;
import com.projedata.factory.service.ProductRawMaterialService;

@Controller
@RequestMapping("/product-raw-materials")
public class ProductRawMaterialController {

    @Autowired
    private ProductRawMaterialService productRawMaterialService;

    @GetMapping("/product/{productId}")
    public ResponseEntity<List<ProductRawMaterialDTO>> getByProduct(@PathVariable Long productId) {
        return ResponseEntity.ok(productRawMaterialService.getByProduct(productId));
    }

    @PostMapping
    public ResponseEntity<ProductRawMaterialDTO> create(@RequestBody @Validated ProductRawMaterialDTO dto) {
        ProductRawMaterialDTO created = productRawMaterialService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductRawMaterialDTO> update(@PathVariable Long id, @RequestBody @Validated ProductRawMaterialDTO dto) {
        return ResponseEntity.ok(productRawMaterialService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        productRawMaterialService.delete(id);
        return ResponseEntity.noContent().build();
    }
}