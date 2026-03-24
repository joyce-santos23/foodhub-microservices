package br.com.foodhub.orderservice.core.application.usecase.order;

import br.com.foodhub.orderservice.core.application.port.order.OrderGateway;
import br.com.foodhub.orderservice.core.domain.entity.order.Order;
import br.com.foodhub.orderservice.core.domain.exceptions.generic.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CancelOrderUseCase {

    private final OrderGateway gateway;

    public void execute(String orderId, String userId) {
        log.info("Cancelando pedido {}", orderId);

        Order order = gateway.findById(orderId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Pedido não encontrado"));

        order.validateOwner(userId);
        order.cancel();

        gateway.save(order);
    }
}
