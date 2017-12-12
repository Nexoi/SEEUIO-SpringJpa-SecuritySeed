package com.seeu.shopper.order.repository;

import com.seeu.shopper.order.model.OrderProduct;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderProductRepository extends JpaRepository<OrderProduct, Long> {
}