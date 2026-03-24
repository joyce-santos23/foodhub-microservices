package br.com.foodhub.paymentservice.infra.integration.kafka;


import br.com.foodhub.contracts.event.OrderCreatedEventDTO;
import br.com.foodhub.paymentservice.core.application.usecase.ProccessPaymentUsecase;
import br.com.foodhub.paymentservice.infra.integration.kafka.mapper.PaymentEventMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class PaymentConsumer {

    private final ProccessPaymentUsecase usecase;

    @KafkaListener(topics = "pedido.criado")
    public void consume(OrderCreatedEventDTO event) {

        log.info("🔥 evento recebido orderId={}", event.orderId());

        var domain = PaymentEventMapper.toDomain(event);

        usecase.execute(domain);
    }
}
