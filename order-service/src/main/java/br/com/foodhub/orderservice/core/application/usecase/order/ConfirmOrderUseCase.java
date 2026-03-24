package br.com.foodhub.orderservice.core.application.usecase.order;

import br.com.foodhub.orderservice.core.application.dto.order.OrderResultDTO;
import br.com.foodhub.orderservice.core.application.port.event.OrderEventPublisher;
import br.com.foodhub.orderservice.core.application.port.order.OrderGateway;
import br.com.foodhub.orderservice.core.domain.entity.order.Order;
import br.com.foodhub.orderservice.core.domain.exceptions.generic.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ConfirmOrderUseCase {

    private final OrderGateway gateway;
    private final OrderEventPublisher eventPublisher;

    public OrderResultDTO execute(String orderId, String userId) {
        log.info("Iniciando confirmação do pedido: " + orderId);
        Order order = gateway.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Pedido não encontrado: " + orderId));

        order.validateOwner(userId);
        order.confirm();

        gateway.save(order);

        eventPublisher.publishOrderCreated(order);
        log.info("Pedido confirmado: " + orderId);

        return OrderResultDTO.from(order);
    }
}
