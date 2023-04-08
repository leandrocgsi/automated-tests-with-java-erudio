package br.com.erudio.mockito;

import java.time.LocalDateTime;
import java.util.UUID;

public class OrderService {

    public Order createOrder(String productName, Long amount, String OrderID) {
        Order order = new Order();
        order.setId(OrderID == null ? UUID.randomUUID().toString() : OrderID);
        order.setCreationDate(LocalDateTime.now());
        order.setAmount(amount);
        order.setProductName(productName);
        return order;
    }
}
