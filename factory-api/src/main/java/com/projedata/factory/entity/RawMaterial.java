package com.projedata.factory.entity;

import java.math.BigDecimal;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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

	public RawMaterial() {}
	
	public RawMaterial(Long id, String name, BigDecimal quantity) {
		this.id = id;
		this.name = name;
		this.quantity = quantity;
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
	

}
