package com.example.order.domain.model;

import com.example.order.domain.valueobjects.Product;
import com.example.sharedkernel.domain.base.AbstractEntity;
import com.example.sharedkernel.domain.base.DomainObjectId;
import com.example.sharedkernel.domain.financial.Currency;
import com.example.sharedkernel.domain.financial.Money;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NonNull;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name="orders")
@Getter
public class Order extends AbstractEntity<OrderId> {

    Money totalPrice;

    @Enumerated(EnumType.STRING)
    @Column(insertable=false, updatable=false)
    Currency currency;

    @Column(name="order_date")
    LocalDateTime orderDate;

    @OneToMany(cascade = CascadeType.ALL,orphanRemoval = true, fetch = FetchType.EAGER)
    private List<OrderItem> orderItemList;

    public Order() {
        super(OrderId.randomId(OrderId.class));
        this.orderDate = LocalDateTime.now();
        this.orderItemList=new ArrayList<>();
    }

    public Order(Currency currency) {
        super(OrderId.randomId(OrderId.class));
        this.orderDate = LocalDateTime.now();
        this.currency=currency;
        totalPrice=new Money(this.currency, 0.0);
        this.orderItemList=new ArrayList<>();
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
        var item  = new OrderItem(product, product.getPrice(), quantity);
        orderItemList.add(item);
        totalPrice=total();
        return item;
    }

    public OrderItem removeItem(@NonNull DomainObjectId orderItemId) {
        Objects.requireNonNull(orderItemId,"Order Item is null");
        //orderItemList.removeIf(v->v.getId().equals(orderItemId));

        OrderItem removedItem=orderItemList.stream().filter(v->v.getId().equals(orderItemId)).findFirst().orElseThrow();
        orderItemList.remove(removedItem);
        totalPrice=total();
        return removedItem;
    }


}
