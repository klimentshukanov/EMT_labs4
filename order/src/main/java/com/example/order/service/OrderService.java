package com.example.order.service;

import com.example.order.domain.exceptions.OrderNotFoundException;
import com.example.order.domain.model.Order;
import com.example.order.domain.model.OrderId;
import com.example.order.domain.model.OrderItemId;
import com.example.order.service.forms.OrderForm;
import com.example.order.service.forms.OrderItemForm;

import java.util.List;
import java.util.Optional;

public interface OrderService {

    OrderId placeOrder(OrderForm orderForm);

    List<Order> findAll();

    Optional<Order> findById(OrderId id);

    void addItem(OrderId orderId, OrderItemForm orderItemForm) throws OrderNotFoundException;

    void deleteItem(OrderId orderId, OrderItemId orderItemId) throws OrderNotFoundException;



}
