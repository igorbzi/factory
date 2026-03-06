package com.projedata.factory.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.projedata.factory.dto.ProductionOptimizationDTO;
import com.projedata.factory.service.ProductionOptimizerService;

@Controller
@RequestMapping("/production")
public class ProductionOptimizerController {

    @Autowired
    private ProductionOptimizerService productionOptimizerService;

    @GetMapping("/optimize")
    public ResponseEntity<ProductionOptimizationDTO> optimize() {
        return ResponseEntity.ok(productionOptimizerService.optimize());
    }
}