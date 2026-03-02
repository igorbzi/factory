package com.projedata.factory.entity;

import java.math.BigDecimal;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;

@Entity
@Table(schema = "public", name = "product_raw_material")
@EqualsAndHashCode(of = "id")
public class ProductRawMaterial {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "raw_material_id")
    private RawMaterial rawMaterial;

    private BigDecimal quantity;

    public ProductRawMaterial() {}

    public ProductRawMaterial(Product product, RawMaterial rawMaterial, BigDecimal quantity) {
        this.product = product;
        this.rawMaterial = rawMaterial;
        this.setQuantity(quantity);
    }
    
    public Long getId() {
    	return id;
    }
    
    public Product getProduct() {
    	return product;
    }
    
    public RawMaterial getRawMaterial() {
    	return rawMaterial;
    }

	public BigDecimal getQuantity() {
		return quantity;
	}

	public void setQuantity(BigDecimal quantity) {
		this.quantity = quantity;
	}

}