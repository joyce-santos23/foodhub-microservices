package br.com.foodhub.orderservice.core.application.usecase.order;

import br.com.foodhub.orderservice.core.application.port.order.OrderGateway;
import br.com.foodhub.orderservice.core.domain.entity.enums.OrderStatus;
import br.com.foodhub.orderservice.core.domain.entity.order.Order;
import br.com.foodhub.orderservice.core.domain.exceptions.generic.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class MarkOrderAsPaidUseCase {

    private final OrderGateway gateway;

    public void execute(String orderId) {
        log.info("Marcando pedido {} como pago", orderId);

        Order order = gateway.findById(orderId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Pedido não encontrado"));

        if (order.getStatus() == OrderStatus.CANCELED) {
            log.warn("Pedido {} cancelado, ignorando pagamento", orderId);
            return;
        }

        order.markPaid();

        gateway.save(order);
    }
}