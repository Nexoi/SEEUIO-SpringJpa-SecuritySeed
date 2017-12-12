package com.seeu.shopper.order.repository;

import com.seeu.shopper.order.model.OrderLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderLogRepository extends JpaRepository<OrderLog, Long> {
}