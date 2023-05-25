package com.example.order.service.impl;

import com.example.order.domain.exceptions.OrderNotFoundException;
import com.example.order.domain.model.Order;
import com.example.order.domain.model.OrderId;
import com.example.order.domain.model.OrderItemId;
import com.example.order.domain.repository.OrderRepository;
import com.example.order.service.OrderService;
import com.example.order.service.forms.OrderForm;
import com.example.order.service.forms.OrderItemForm;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolationException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import jakarta.validation.Validator;


import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@Transactional
@AllArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    //private final DomainEventPublisher domainEventPublisher;
    private final Validator validator;

    @Override
    public OrderId placeOrder(OrderForm orderForm) {
        Objects.requireNonNull(orderForm,"Order must not be null.");
        var constraintViolations = validator.validate(orderForm);
        if (constraintViolations.size()>0) {
            throw new ConstraintViolationException("The order form is not valid", constraintViolations);
        }
        var newOrder = orderRepository.saveAndFlush(toDomainObject(orderForm));
        //newOrder.getOrderItemList().forEach(item->domainEventPublisher.publish(new OrderItemCreated(item.getProductId().getId(),item.getQuantity())));
        return newOrder.getId();
    }

    @Override
    public List<Order> findAll() {
        return orderRepository.findAll();
    }

    @Override
    public Optional<Order> findById(OrderId id) {
        return orderRepository.findById(id);
    }

    @Override
    public void addItem(OrderId orderId, OrderItemForm orderItemForm) throws OrderNotFoundException {
        Order order = orderRepository.findById(orderId).orElseThrow(OrderNotFoundException::new);
        order.addItem(orderItemForm.getProduct(),orderItemForm.getQuantity());
        orderRepository.saveAndFlush(order);
        //domainEventPublisher.publish(new OrderItemCreated(orderItemForm.getProduct().getId().getId(),orderItemForm.getQuantity()));
    }

    @Override
    public void deleteItem(OrderId orderId, OrderItemId orderItemId) throws OrderNotFoundException {
        Order order = orderRepository.findById(orderId).orElseThrow(OrderNotFoundException::new);
        order.removeItem(orderItemId);
        orderRepository.saveAndFlush(order);
    }

    private Order toDomainObject(OrderForm orderForm) {
        var order = new Order(orderForm.getCurrency());
        orderForm.getItems().forEach(item->order.addItem(item.getProduct(),item.getQuantity()));
        return order;
    }
}
