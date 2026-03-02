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

import com.projedata.factory.dto.RawMaterialDTO;
import com.projedata.factory.service.RawMaterialService;

@Controller
@RequestMapping("/raw-materials")
public class RawMaterialController {

    @Autowired
    private RawMaterialService rawMaterialService;

    @GetMapping
    public ResponseEntity<List<RawMaterialDTO>> getAll() {
        return ResponseEntity.ok(rawMaterialService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<RawMaterialDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(rawMaterialService.getById(id));
    }

    @PostMapping
    public ResponseEntity<RawMaterialDTO> create(@RequestBody @Validated RawMaterialDTO dto) {
        RawMaterialDTO created = rawMaterialService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RawMaterialDTO> update(@PathVariable Long id, @RequestBody @Validated RawMaterialDTO dto) {
        return ResponseEntity.ok(rawMaterialService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        rawMaterialService.delete(id);
        return ResponseEntity.noContent().build();
    }
}