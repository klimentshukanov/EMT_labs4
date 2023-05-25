package com.example.order.domain.model;

import com.example.product.domain.model.Product;
import com.example.sharedkernel.domain.base.AbstractEntity;
import com.example.sharedkernel.domain.financial.Currency;
import com.example.sharedkernel.domain.financial.Money;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NonNull;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name="orders")
@Getter
public class Order extends AbstractEntity<OrderId> {

    Money totalPrice;

    Currency currency;

    @Column(name="order_date")
    LocalDateTime orderDate;

    @OneToMany(cascade = CascadeType.ALL,orphanRemoval = true, fetch = FetchType.EAGER)
    private List<OrderItem> orderItemList = new ArrayList<>();

    public Order() {
        super(OrderId.randomId(OrderId.class));
        this.orderDate = LocalDateTime.now();
    }

    public Order(Currency currency) {
        super(OrderId.randomId(OrderId.class));
        this.orderDate = LocalDateTime.now();
        this.currency=currency;
        totalPrice=new Money(this.currency, 0.0);
    }

    public Money total() {
        double total=0;
        for (OrderItem orderItem : orderItemList)
        {
            total += orderItem.itemPrice.getAmount() * orderItem.quantity;
        }
        return new Money(this.currency, total);
    }

    public OrderItem addItem(@NonNull Product product, int quantity) {
        Objects.requireNonNull(product,"Product is null");
        var item  = new OrderItem(product.getId(),product.getPrice(),quantity);
        orderItemList.add(item);
        totalPrice=total();
        return item;
    }

    public void removeItem(@NonNull OrderItemId orderItemId) {
        Objects.requireNonNull(orderItemId,"Order Item is null");
        orderItemList.removeIf(v->v.getId().equals(orderItemId));
        totalPrice=total();
    }


}
