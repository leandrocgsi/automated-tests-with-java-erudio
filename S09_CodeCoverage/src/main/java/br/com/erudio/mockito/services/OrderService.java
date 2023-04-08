package br.com.erudio.mockito.services;

import java.time.LocalDateTime;
import java.util.UUID;

public class OrderService {

    public Order createOrder(String productName, Long amount, String orderID) {
        
        Order order = new Order();
        
        order.setId(orderID == null ? UUID.randomUUID().toString() : orderID);
        order.setCreationDate(LocalDateTime.now());
        order.setAmount(amount);
        order.setProductName(productName);
        return order;
    }
}
