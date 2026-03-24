package br.com.foodhub.orderservice.core.application.usecase.order;

import br.com.foodhub.orderservice.core.application.port.order.OrderGateway;
import br.com.foodhub.orderservice.core.domain.entity.order.Order;
import br.com.foodhub.orderservice.core.domain.exceptions.generic.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MarkOrderAsPendingUseCase {

    private final OrderGateway gateway;

    public void execute(String orderId) {

        Order order = gateway.findById(orderId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Pedido não encontrado"));

        order.markPendingPayment();

        gateway.save(order);
    }
}
