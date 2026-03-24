package br.com.foodhub.paymentservice.infra.integration.kafka.mapper;

import br.com.foodhub.contracts.event.OrderCreatedEventDTO;

public class PaymentEventMapper {

    private PaymentEventMapper() {
    }

    public static br.com.foodhub.paymentservice.core.application.dto.OrderCreatedEventDTO toDomain(
            OrderCreatedEventDTO event
    ) {
        return new br.com.foodhub.paymentservice.core.application.dto.OrderCreatedEventDTO(
                event.orderId(),
                event.userId(),
                event.amount(),
                event.attempts()
        );
    }

    public static OrderCreatedEventDTO toEvent(
            br.com.foodhub.paymentservice.core.application.dto.OrderCreatedEventDTO domain
    ) {
        return new OrderCreatedEventDTO(
                domain.orderId(),
                domain.userId(),
                domain.amount(),
                domain.attempts()
        );
    }
}
