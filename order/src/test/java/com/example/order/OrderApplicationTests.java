package com.example.order;

import com.example.order.domain.exceptions.OrderNotFoundException;
import com.example.order.domain.model.Order;
import com.example.order.domain.model.OrderId;
import com.example.order.domain.model.OrderItem;
import com.example.order.domain.model.OrderItemId;
import com.example.order.domain.valueobjects.Product;
import com.example.order.service.OrderService;
import com.example.order.service.forms.OrderForm;
import com.example.sharedkernel.domain.financial.Currency;
import com.example.sharedkernel.domain.financial.Money;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@SpringBootTest
class OrderApplicationTests {

   @Autowired
   private OrderService orderService;

    private OrderId createOrder() {
        Product product1=new Product("PC", 3, new Money(Currency.EUR, 250.0), "Gaming PC");
        OrderItem orderItem1 = new OrderItem(product1, product1.getPrice(), 2);

        Product product2=new Product("Headphones", 5, new Money(Currency.EUR, 150.0), "Gaming Headphones");
        OrderItem orderItem2 = new OrderItem(product2, product2.getPrice(), 2);


        List<OrderItem> orderItems = new ArrayList<>();
        orderItems.add(orderItem1);
        orderItems.add(orderItem2);

        OrderForm orderForm=new OrderForm(Currency.EUR, orderItems);

        return orderService.placeOrder(orderForm);
    }

    @Test
    void contextLoads() {
    }

    @Test
    public void makeOrder()
    {

        OrderId orderId = createOrder();
        Order order=orderService.findById(orderId).orElse(null);
        Assertions.assertNotNull(order);
        Assertions.assertEquals(order.getTotalPrice().getAmount(), 800.0, 0);

    }


    @Test
    public void addItemToOrder()
    {
        OrderId orderId = createOrder();
        Order order=orderService.findById(orderId).orElseThrow(OrderNotFoundException::new);
        order.addItem(new Product("Laptop", 7, new Money(Currency.EUR, 120.0), "Gaming Laptop"), 1);

        Assertions.assertEquals(order.getTotalPrice().getAmount(), 920.0, 0);

    }

    @Test
    public void deleteItemFromOrder()
    {
        OrderId orderId = createOrder();
        Order order=orderService.findById(orderId).orElseThrow(OrderNotFoundException::new);

        OrderItem orderItem=order.getOrderItemList().stream().findFirst().orElseThrow(NoSuchElementException::new);

        order.removeItem(orderItem.getId());

        Assertions.assertEquals(order.getTotalPrice().getAmount(), 300.0, 0);

    }

}
