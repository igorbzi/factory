package com.projedata.factory.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.projedata.factory.entity.RawMaterial;

public interface RawMaterialRepository extends JpaRepository<RawMaterial, Long>{
	
}
