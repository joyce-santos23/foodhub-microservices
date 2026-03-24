package br.com.foodhub.orderservice.core.application.port.payment;

import br.com.foodhub.orderservice.core.domain.entity.order.Order;

public interface PaymentIntegrationPort {

    void processPayment(Order order);
}
