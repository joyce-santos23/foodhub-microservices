package br.com.foodhub.orderservice.core.application.port.event;

import br.com.foodhub.orderservice.core.domain.entity.order.Order;

public interface OrderEventPublisher {

    void publishOrderCreated(Order order);

}