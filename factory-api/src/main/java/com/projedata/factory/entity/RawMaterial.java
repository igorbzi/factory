package com.projedata.factory.entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.projedata.factory.enumerators.MeasurementUnit;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;

@Entity(name = "raw_material")
@EqualsAndHashCode(of = "id")
@Table(schema="public", name="raw_material")
public class RawMaterial {

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String name;
	
	private BigDecimal quantity;
	
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private MeasurementUnit unitOfMeasure;
	
	@OneToMany(mappedBy = "rawMaterial")
    private List<ProductRawMaterial> productRawMaterials = new ArrayList<>();

	public RawMaterial() {}
	
	public RawMaterial(Long id, String name, BigDecimal quantity, MeasurementUnit unitOfMeasure) {
		this.id = id;
		this.name = name;
		this.quantity = quantity;
		this.unitOfMeasure = unitOfMeasure;
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

	public BigDecimal getQuantity() {
		return quantity;
	}

	public void setQuantity(BigDecimal quantity) {
		this.quantity = quantity;
	}
	
	public List<ProductRawMaterial> getProductRawMaterials() { 
		 return productRawMaterials; 
	}
	
	public MeasurementUnit getMeasurementUnit() {
		return unitOfMeasure;
	}
	
	public void setMeasurementUnit(MeasurementUnit u) {
		this.unitOfMeasure = u;
	}

}
