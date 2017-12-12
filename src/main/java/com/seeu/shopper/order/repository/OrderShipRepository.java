package com.seeu.shopper.order.repository;

import com.seeu.shopper.order.model.OrderShip;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderShipRepository extends JpaRepository<OrderShip, Long> {
}