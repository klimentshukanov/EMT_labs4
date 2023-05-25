package com.example.product.domain.model;

import com.example.sharedkernel.domain.base.AbstractEntity;
import com.example.sharedkernel.domain.financial.Money;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "product")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Product extends AbstractEntity<ProductId> {

    @Column(name="product_name")
    String productName;

    @Column(name="quantity")
    Integer quantity;

    Money price;

    @Column(name="productDescription")
    String productDescription;

}
