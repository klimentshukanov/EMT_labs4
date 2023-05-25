package com.example.order.domain.valueobjects;

import com.example.sharedkernel.domain.base.ValueObject;
import com.example.sharedkernel.domain.financial.Money;
import lombok.Data;

@Data
public class Product implements ValueObject {

    String productName;

    Integer quantity;

    Money price;

    String productDescription;

    public Product() {
    }

    public Product(String productName, Integer quantity, Money price, String productDescription) {
        this.productName = productName;
        this.quantity = quantity;
        this.price = price;
        this.productDescription = productDescription;
    }
}
