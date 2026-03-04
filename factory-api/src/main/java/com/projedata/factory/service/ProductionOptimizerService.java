package com.projedata.factory.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.ojalgo.optimisation.Expression;
import org.ojalgo.optimisation.ExpressionsBasedModel;
import org.ojalgo.optimisation.Optimisation;
import org.ojalgo.optimisation.Variable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.projedata.factory.dto.ProductionOptimizationDTO;
import com.projedata.factory.dto.ProductionSuggestionDTO;
import com.projedata.factory.entity.Product;
import com.projedata.factory.entity.RawMaterial;
import com.projedata.factory.repository.ProductRepository;
import com.projedata.factory.repository.RawMaterialRepository;

@Service
public class ProductionOptimizerService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private RawMaterialRepository rawMaterialRepository;

    public ProductionOptimizationDTO optimize() {
        List<Product> products = productRepository.findAll();
        Map<Long, BigDecimal> stock = buildStockMap();

        ExpressionsBasedModel model = new ExpressionsBasedModel();

        //variables
        Map<Long, Variable> variables = new LinkedHashMap<>();
        for (Product product : products) {
            int maxProducible = calculateProducibleQuantity(product, stock);
            if (maxProducible == 0) continue;

            Variable var = model.addVariable(String.valueOf(product.getId()))
                    .integer(true)
                    .lower(0)
                    .upper(maxProducible);

            variables.put(product.getId(), var);
        }

        //constraints
        for (Map.Entry<Long, BigDecimal> entry : stock.entrySet()) {
            Long materialId = entry.getKey();
            BigDecimal available = entry.getValue();

            Expression constraint = model.addExpression("stock_" + materialId).upper(available);

            for (Product product : products) {
                Variable var = variables.get(product.getId());
                if (var == null) continue;

                product.getRawMaterials().stream()
                        .filter(i -> i.getRawMaterial().getId().equals(materialId))
                        .findFirst()
                        .ifPresent(i -> constraint.set(var, i.getQuantity()));
            }
        }
        
        //objective function
        Expression objective = model.addExpression("total_revenue").weight(BigDecimal.ONE);
        for (Product product : products) {
            Variable var = variables.get(product.getId());
            if (var != null) {
                objective.set(var, product.getPrice());
            }
        }

        //maximize
        Optimisation.Result result = model.maximise();

        
        List<ProductionSuggestionDTO> suggestions = new ArrayList<>();
        BigDecimal totalRevenue = BigDecimal.ZERO;

        for (Product product : products) {
            Variable var = variables.get(product.getId());
            if (var == null) continue;

            int qty = result.get(model.indexOf(var)).intValue();
            if (qty == 0) continue;

            BigDecimal revenue = product.getPrice().multiply(BigDecimal.valueOf(qty));
            suggestions.add(new ProductionSuggestionDTO(
                product.getId(),
                product.getName(),
                product.getPrice(),
                qty,
                revenue
            ));
            totalRevenue = totalRevenue.add(revenue);
        }

        return new ProductionOptimizationDTO(suggestions, totalRevenue);
    }

    private int calculateProducibleQuantity(Product product, Map<Long, BigDecimal> stock) {
        return product.getRawMaterials().stream()
                .mapToInt(ingredient -> {
                    BigDecimal available = stock.getOrDefault(ingredient.getRawMaterial().getId(), BigDecimal.ZERO);
                    return available.divide(ingredient.getQuantity(), 0, RoundingMode.FLOOR).intValue();
                })
                .min()
                .orElse(0);
    }

    private Map<Long, BigDecimal> buildStockMap() {
        return rawMaterialRepository.findAll().stream()
                .collect(Collectors.toMap(RawMaterial::getId, RawMaterial::getQuantity));
    }
}