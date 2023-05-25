package com.example.order.domain.repository;

import com.example.order.domain.model.OrderItem;
import com.example.order.domain.model.OrderItemId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, OrderItemId> {
}
