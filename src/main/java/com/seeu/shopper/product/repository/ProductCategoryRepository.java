package com.seeu.shopper.product.repository;

import com.seeu.shopper.product.model.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductCategoryRepository extends JpaRepository<ProductCategory, Long> {
    ProductCategory findFirstByName(String name);

    List<ProductCategory> findAllByFatherIdIsNull();
}