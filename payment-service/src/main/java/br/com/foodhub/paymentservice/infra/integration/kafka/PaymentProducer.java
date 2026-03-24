package br.com.foodhub.paymentservice.infra.integration.kafka;

import br.com.foodhub.paymentservice.core.application.dto.OrderCreatedEventDTO;

import br.com.foodhub.paymentservice.infra.integration.kafka.mapper.PaymentEventMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class PaymentProducer {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void sendApproved(OrderCreatedEventDTO domain) {
        log.info("📤 enviando pagamento.aprovado orderId={}", domain.orderId());

        var event = PaymentEventMapper.toEvent(domain);

        kafkaTemplate.send("pagamento.aprovado", event);
    }

    public void sendPending(OrderCreatedEventDTO domain) {
        log.warn("📤 enviando pagamento.pendente orderId={}", domain.orderId());

        var event = PaymentEventMapper.toEvent(domain);

        kafkaTemplate.send("pagamento.pendente", event);
    }

    public void sendToDLQ(OrderCreatedEventDTO domain) {
        log.error("💀 pagamento.dlq orderId={}", domain.orderId());

        var event = PaymentEventMapper.toEvent(domain);

        kafkaTemplate.send("pagamento.dlq", event);
    }
}