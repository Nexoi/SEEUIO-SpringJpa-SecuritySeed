package com.seeu.shopper.product.repository;

import com.seeu.shopper.product.model.ProductStockLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductStockLogRepository extends JpaRepository<ProductStockLog,Long> {
}