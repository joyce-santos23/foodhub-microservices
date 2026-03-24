package br.com.foodhub.orderservice.infra.integration.kafka.producer;

import br.com.foodhub.contracts.event.OrderCreatedEventDTO;
import br.com.foodhub.orderservice.core.application.port.event.OrderEventPublisher;
import br.com.foodhub.orderservice.core.domain.entity.order.Order;
import br.com.foodhub.orderservice.infra.persistence.mapper.kafka.OrderEventMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class KafkaOrderEventPublisher implements OrderEventPublisher {

    private final KafkaTemplate<String, OrderCreatedEventDTO> kafkaTemplate;

    private static final String TOPIC = "pedido.criado";

    @Override
    public void publishOrderCreated(Order order) {
        log.info("Publicando evento de pedido criado. orderId={}", order.getId());

        OrderCreatedEventDTO event = OrderEventMapper.toEvent(order);

        kafkaTemplate.send(TOPIC, order.getId(), event);
    }
}