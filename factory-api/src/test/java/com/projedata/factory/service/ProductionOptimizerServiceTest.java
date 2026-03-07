package com.projedata.factory.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.projedata.factory.dto.ProductionOptimizationDTO;
import com.projedata.factory.dto.ProductionSuggestionDTO;
import com.projedata.factory.entity.Product;
import com.projedata.factory.entity.ProductRawMaterial;
import com.projedata.factory.entity.RawMaterial;
import com.projedata.factory.enumerators.MeasurementUnit;
import com.projedata.factory.repository.ProductRepository;
import com.projedata.factory.repository.RawMaterialRepository;

@ExtendWith(MockitoExtension.class)
public class ProductionOptimizerServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private RawMaterialRepository rawMaterialRepository;

    @InjectMocks
    private ProductionOptimizerService productionOptimizerService;

    private RawMaterial steel;
    private RawMaterial plastic;

    @BeforeEach
    void setUp() {
        steel   = new RawMaterial(1L, "Steel",   new BigDecimal("100"), MeasurementUnit.G);
        plastic = new RawMaterial(2L, "Plastic", new BigDecimal("50"),  MeasurementUnit.G);
    }

    @Test
    @DisplayName("Should return empty plan when there are no products")
    void optimize_noProducts_returnsEmptyPlan() {
        when(productRepository.findAll()).thenReturn(List.of());
        when(rawMaterialRepository.findAll()).thenReturn(List.of(steel));

        ProductionOptimizationDTO result = productionOptimizerService.optimize();
        
        assertThat(result.suggestions()).isEmpty();
        assertThat(result.totalRevenue()).isEqualByComparingTo(BigDecimal.ZERO);
    }

    @Test
    @DisplayName("Should return empty plan when stock is insufficient for any product")
    void optimize_insufficientStock_returnsEmptyPlan() {
        steel = new RawMaterial(1L, "Steel", new BigDecimal("0"), MeasurementUnit.G);

        Product product = buildProduct(1L, "Chair", new BigDecimal("200"),
                buildIngredient(steel, new BigDecimal("10")));

        when(productRepository.findAll()).thenReturn(List.of(product));
        when(rawMaterialRepository.findAll()).thenReturn(List.of(steel));

        ProductionOptimizationDTO result = productionOptimizerService.optimize();
        System.out.println(result);
        
        assertThat(result.suggestions()).isEmpty();
        assertThat(result.totalRevenue()).isEqualByComparingTo(BigDecimal.ZERO);
    }

    @Test
    @DisplayName("Should suggest correct quantity based on available stock")
    void optimize_sufficientStock_returnsCorrectQuantity() {

        Product product = buildProduct(1L, "Chair", new BigDecimal("200"),
                buildIngredient(steel, new BigDecimal("10")));

        when(productRepository.findAll()).thenReturn(List.of(product));
        when(rawMaterialRepository.findAll()).thenReturn(List.of(steel));

        ProductionOptimizationDTO result = productionOptimizerService.optimize();
        
        assertThat(result.suggestions()).hasSize(1);
        assertThat(result.suggestions().get(0).quantity()).isEqualTo(10);
        assertThat(result.totalRevenue()).isEqualByComparingTo(new BigDecimal("2000"));
    }

    @Test
    @DisplayName("Should limit quantity to the scarcest raw material")
    void optimize_multipleIngredients_limitedByScarcestMaterial() {

        Product product = buildProduct(1L, "Chair", new BigDecimal("200"),
                buildIngredient(steel, new BigDecimal("10")),
                buildIngredient(plastic, new BigDecimal("25")));

        when(productRepository.findAll()).thenReturn(List.of(product));
        when(rawMaterialRepository.findAll()).thenReturn(List.of(steel, plastic));

        ProductionOptimizationDTO result = productionOptimizerService.optimize();
        
        assertThat(result.suggestions()).hasSize(1);
        assertThat(result.suggestions().get(0).quantity()).isEqualTo(2);
        assertThat(result.totalRevenue()).isEqualByComparingTo(new BigDecimal("400"));
    }

    @Test
    @DisplayName("Should resolve conflict prioritizing product with highest total revenue")
    void optimize_conflictingProducts_prioritizesHighestTotalRevenue() {
    	
        Product chair = buildProduct(1L, "Chair", new BigDecimal("200"),
                buildIngredient(steel, new BigDecimal("50")));
        Product table = buildProduct(2L, "Table", new BigDecimal("150"),
                buildIngredient(steel, new BigDecimal("25")));

        when(productRepository.findAll()).thenReturn(List.of(chair, table));
        when(rawMaterialRepository.findAll()).thenReturn(List.of(steel));

        ProductionOptimizationDTO result = productionOptimizerService.optimize();

        ProductionSuggestionDTO firstSuggestion = result.suggestions().get(0);
        assertThat(firstSuggestion.productName()).isEqualTo("Table");
    }

    @Test
    @DisplayName("Should produce both products when stock allows")
    void optimize_enoughStockForBoth_returnsBothProducts() {
        steel = new RawMaterial(1L, "Steel", new BigDecimal("100"), MeasurementUnit.G);

        Product chair = buildProduct(1L, "Chair", new BigDecimal("100"),
                buildIngredient(steel, new BigDecimal("17")));
        Product table = buildProduct(2L, "Table", new BigDecimal("200"),
                buildIngredient(steel, new BigDecimal("43")));

        when(productRepository.findAll()).thenReturn(List.of(chair, table));
        when(rawMaterialRepository.findAll()).thenReturn(List.of(steel));

        ProductionOptimizationDTO result = productionOptimizerService.optimize();

        assertThat(result.suggestions()).hasSize(2);
    }

    @Test
    @DisplayName("Should calculate total revenue correctly")
    void optimize_multipleSuggestions_totalRevenueIsCorrect() {
        steel = new RawMaterial(1L, "Steel", new BigDecimal("200"), MeasurementUnit.G);

        Product chair = buildProduct(1L, "Chair", new BigDecimal("200"),
                buildIngredient(steel, new BigDecimal("50")));
        Product table = buildProduct(2L, "Table", new BigDecimal("150"),
                buildIngredient(steel, new BigDecimal("25")));

        when(productRepository.findAll()).thenReturn(List.of(chair, table));
        when(rawMaterialRepository.findAll()).thenReturn(List.of(steel));

        ProductionOptimizationDTO result = productionOptimizerService.optimize();

        
        assertThat(result.totalRevenue()).isEqualByComparingTo(new BigDecimal("1200"));
    }
    
    @Test
    @DisplayName("Should produce correct quantity with two raw materials")
    void optimize_twoRawMaterials_correctQuantity() {

        Product chair = buildProduct(1L, "Chair", new BigDecimal("200"),
                buildIngredient(steel, new BigDecimal("10")),
                buildIngredient(plastic, new BigDecimal("10")));

        when(productRepository.findAll()).thenReturn(List.of(chair));
        when(rawMaterialRepository.findAll()).thenReturn(List.of(steel, plastic));

        ProductionOptimizationDTO result = productionOptimizerService.optimize();
        System.out.println(result);
        
        assertThat(result.suggestions()).hasSize(1);
        assertThat(result.suggestions().get(0).quantity()).isEqualTo(5);
        assertThat(result.totalRevenue()).isEqualByComparingTo(new BigDecimal("1000"));
    }

    @Test
    @DisplayName("Should resolve conflict between two products sharing two raw materials")
    void optimize_twoProductsTwoMaterials_prioritizesHighestRevenue() {
        Product chair = buildProduct(1L, "Chair", new BigDecimal("200"),
                buildIngredient(steel, new BigDecimal("10")),
                buildIngredient(plastic, new BigDecimal("10")));
        Product table = buildProduct(2L, "Table", new BigDecimal("300"),
                buildIngredient(steel, new BigDecimal("20")),
                buildIngredient(plastic, new BigDecimal("5")));

        when(productRepository.findAll()).thenReturn(List.of(chair, table));
        when(rawMaterialRepository.findAll()).thenReturn(List.of(steel, plastic));

        ProductionOptimizationDTO result = productionOptimizerService.optimize();
        
        assertThat(result.suggestions()).hasSize(2);
        assertThat(result.totalRevenue()).isEqualByComparingTo(new BigDecimal("1600"));
    }

    @Test
    @DisplayName("Should produce both products when two raw materials allow")
    void optimize_twoProductsTwoMaterials_producesBothWhenStockAllows() {

        steel = new RawMaterial(1L, "Steel", new BigDecimal("200"), MeasurementUnit.G);
        plastic = new RawMaterial(2L, "Plastic", new BigDecimal("50"), MeasurementUnit.G);

        Product chair = buildProduct(1L, "Chair", new BigDecimal("200"),
                buildIngredient(steel, new BigDecimal("10")),
                buildIngredient(plastic, new BigDecimal("5")));
        Product table = buildProduct(2L, "Table", new BigDecimal("300"),
                buildIngredient(steel, new BigDecimal("20")),
                buildIngredient(plastic, new BigDecimal("2")));

        when(productRepository.findAll()).thenReturn(List.of(chair, table));
        when(rawMaterialRepository.findAll()).thenReturn(List.of(steel, plastic));

        ProductionOptimizationDTO result = productionOptimizerService.optimize();
        
        assertThat(result.suggestions()).hasSize(2);
        assertThat(result.totalRevenue()).isPositive();
    }

    @Test
    @DisplayName("Should return empty when both materials are insufficient")
    void optimize_twoRawMaterials_bothInsufficient_returnsEmpty() {
        steel = new RawMaterial(1L, "Steel", new BigDecimal("5"), MeasurementUnit.G);
        plastic = new RawMaterial(2L, "Plastic", new BigDecimal("2"), MeasurementUnit.G);

        Product chair = buildProduct(1L, "Chair", new BigDecimal("200"),
                buildIngredient(steel, new BigDecimal("10")),
                buildIngredient(plastic, new BigDecimal("5")));

        when(productRepository.findAll()).thenReturn(List.of(chair));
        when(rawMaterialRepository.findAll()).thenReturn(List.of(steel, plastic));

        ProductionOptimizationDTO result = productionOptimizerService.optimize();
        
        assertThat(result.suggestions()).isEmpty();
        assertThat(result.totalRevenue()).isEqualByComparingTo(BigDecimal.ZERO);
    }

    // ─── Helpers ──────────────────────────────────────────────────────────────

    private Product buildProduct(Long id, String name, BigDecimal price, ProductRawMaterial... ingredients) {
        return new Product(id, name, price, new ArrayList<>(Arrays.asList(ingredients)));
    }
    
    private ProductRawMaterial buildIngredient(RawMaterial material, BigDecimal quantity) {
        return new ProductRawMaterial(null, material, quantity);
    }
}