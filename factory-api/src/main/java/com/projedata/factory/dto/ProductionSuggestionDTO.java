package com.projedata.factory.dto;

import java.math.BigDecimal;

public record ProductionSuggestionDTO(
	    Long productId,
	    String productName,
	    BigDecimal unitPrice,
	    int quantity,
	    BigDecimal totalRevenue
	) {}
