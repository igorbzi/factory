package com.projedata.factory.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.projedata.factory.entity.Product;



@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query("SELECT DISTINCT p FROM product p LEFT JOIN FETCH p.rawMaterials rm LEFT JOIN FETCH rm.rawMaterial")
    List<Product> findAllWithIngredients();

    @Query("SELECT p FROM product p LEFT JOIN FETCH p.rawMaterials rm LEFT JOIN FETCH rm.rawMaterial WHERE p.id = :id")
    Optional<Product> findByIdWithIngredients(@Param("id") Long id);
}