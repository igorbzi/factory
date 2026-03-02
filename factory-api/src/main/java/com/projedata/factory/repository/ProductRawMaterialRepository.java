package com.projedata.factory.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.projedata.factory.entity.ProductRawMaterial;

@Repository
public interface ProductRawMaterialRepository extends JpaRepository<ProductRawMaterial, Long> {
    List<ProductRawMaterial> findByProductId(Long productId);
}