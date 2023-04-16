package br.com.erudio.mockito.constructor;

import java.math.BigDecimal;

public class CheckoutService {
    
    public BigDecimal purchaseProduct(String productName, String customerId) {
        
        // Any arbitrary implementation
        PaymentProcessor paymentProcessor = new PaymentProcessor();
        return paymentProcessor.chargeCustomer(customerId, BigDecimal.TEN);
    }
}
