package com.seeu.shopper.order.repository;

import com.seeu.shopper.order.model.OrderBasic;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderBasicRepository extends JpaRepository<OrderBasic, Long> {
}