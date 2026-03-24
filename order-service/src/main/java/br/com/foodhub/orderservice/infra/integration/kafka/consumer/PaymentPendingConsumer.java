package br.com.foodhub.orderservice.infra.integration.kafka.consumer;

import br.com.foodhub.contracts.event.OrderCreatedEventDTO;
import br.com.foodhub.orderservice.core.application.usecase.order.MarkOrderAsPendingUseCase;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class PaymentPendingConsumer {

    private final MarkOrderAsPendingUseCase useCase;

    @KafkaListener(topics = "pagamento.pendente", groupId = "order-service")
    public void consume(OrderCreatedEventDTO event) {

        log.warn("⚠ pagamento pendente recebido orderId={}", event.orderId());

        useCase.execute(event.orderId());
    }
}
