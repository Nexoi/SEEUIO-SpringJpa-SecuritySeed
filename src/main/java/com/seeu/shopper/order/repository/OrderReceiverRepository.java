package com.seeu.shopper.order.repository;

import com.seeu.shopper.order.model.OrderReceiver;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderReceiverRepository extends JpaRepository<OrderReceiver, Long> {
}