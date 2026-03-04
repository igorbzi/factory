package com.projedata.factory.dto;

import java.math.BigDecimal;
import java.util.List;

public record ProductionOptimizationDTO(
	    List<ProductionSuggestionDTO> suggestions,
	    BigDecimal totalRevenue
) {}