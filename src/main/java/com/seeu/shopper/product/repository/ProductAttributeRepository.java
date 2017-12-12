package com.seeu.shopper.product.repository;

import com.seeu.shopper.product.model.ProductAttribute;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductAttributeRepository extends JpaRepository<ProductAttribute, Long> {
    List<ProductAttribute> findAllByPid(@Param("pid") Long pid);
}