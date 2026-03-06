package com.projedata.factory.entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;

@Entity(name = "product")
@EqualsAndHashCode(of = "id")
@Table(schema="public", name="product")
public class Product {
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String name;
	
	private BigDecimal price;
	
	@OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<ProductRawMaterial> rawMaterials = new ArrayList<>();

	public Product() {}
	
    public Product(Long id, String name, BigDecimal price, List<ProductRawMaterial> rawMaterials) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.rawMaterials = rawMaterials != null ? rawMaterials : new ArrayList<>();
    }
	
	public Long getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	
	public List<ProductRawMaterial> getRawMaterials(){
		return rawMaterials;
	}


}
