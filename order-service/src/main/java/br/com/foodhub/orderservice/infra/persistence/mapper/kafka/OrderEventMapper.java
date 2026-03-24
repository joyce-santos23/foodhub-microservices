package br.com.foodhub.orderservice.infra.persistence.mapper.kafka;

import br.com.foodhub.contracts.event.OrderCreatedEventDTO;
import br.com.foodhub.orderservice.core.domain.entity.order.Order;

public class OrderEventMapper {

    public static OrderCreatedEventDTO toEvent(Order order) {
        return new OrderCreatedEventDTO(
                order.getId(),
                order.getUserId(),
                order.getTotalAmount(),
                0 // começa com 0 tentativas
        );
    }
}