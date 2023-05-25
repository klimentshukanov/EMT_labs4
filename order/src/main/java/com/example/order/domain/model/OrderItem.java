package com.example.order.domain.model;

import com.example.order.domain.valueobjects.Product;
import com.example.sharedkernel.domain.base.AbstractEntity;
import com.example.sharedkernel.domain.base.DomainObjectId;
import com.example.sharedkernel.domain.financial.Money;
import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NonNull;

@Entity
@Table(name = "order_item")
@Getter
public class OrderItem extends AbstractEntity<DomainObjectId> {

    Money itemPrice;

    @Column(length = 1000)
    Product product;

    @Column(name="quantity")
    Integer quantity;


    public OrderItem() {
        super(DomainObjectId.randomId(OrderItemId.class));
    }

    public OrderItem(@NonNull Product product, @NonNull Money itemPrice, int qty) {
        super(DomainObjectId.randomId(OrderItemId.class));
        this.product = product;
        this.itemPrice = itemPrice;
        this.quantity = qty;
    }


}
